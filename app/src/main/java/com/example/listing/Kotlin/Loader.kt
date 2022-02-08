package com.example.listing.Kotlin

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listing.*
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.Material.Loader.LoaderFragment
import com.example.listing.Plan.PlanFragment
import com.example.listing.Utils.Loginsession
import com.example.listing.ViewModelsFactory.PlansDataModelFactory
import com.example.listing.models.Material
import com.example.listing.models.Plan
import com.example.listing.models.VechAssignLoader
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Loader : AppCompatActivity(), PlanClickListener ,  Offlineitem_updatelist_loader {
    var reqs = ArrayList<Plan?>()
    lateinit var model: PlansDataModel
    var po = 0
    lateinit var  progressBar: LinearLayout
    var main_layout: ConstraintLayout? = null

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        this.getSupportActionBar()!!.hide()
        lateinit var logout : FloatingActionButton

        progressBar = findViewById(R.id.progressBarlayout)
        main_layout=findViewById(R.id.mainlayout)

        val android_id = Settings.Secure.getString(
            this.getContentResolver(),
            Settings.Secure.ANDROID_ID
        )

        model = ViewModelProvider(this, PlansDataModelFactory(this.application, android_id)).get(
            PlansDataModel::class.java
        )

      //  model.postDriver()
        model.getplansLoader(this)
        model.UserRule.value=true
        progressBar.visibility = View.VISIBLE
       logout=findViewById(R.id.logout_button);



        model.Plans.observe(this,
            { Plans: List<Plan?>? ->
                progressBar.visibility = View.GONE
                buildRecycler(
                    (Plans as ArrayList<Plan?>?)!!
                )
            })

        model.VechAssignLoaderList.observe(this, { StatusList: List<VechAssignLoader?>? ->
            if (StatusList != null) {
                if (StatusList.isNotEmpty()) {
                    Offline_items_PopUpView(StatusList)
                }
            }


        })


        logout.setOnClickListener {
            Loginsession.getInstance().user=null
            var intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }


    }

    fun buildRecycler(lst: ArrayList<Plan?>) {
        var planFragment = PlanFragment.newInstance(lst, true)
        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        ft.replace(R.id.mainlayout, planFragment)
        ft.commit()
    }




    override fun onItemClick(plan: Plan?, pos: Int) {
        Loginsession.getInstance().setOfflineflag(true)
        model.offlineLoaderItems(this, plan?.ZuphrLpid)
        model.plan.value = plan
        LoaderFragmentInteraction(plan!!, pos)
        po = pos


    }

    fun LoaderFragmentInteraction(ppln: Plan, pos: Int) {

        model.plan.observe(this, { plan: Plan? ->
            model.MatrialsList.value = plan?.planToItems


        })

        model.MatrialsList.observe(this, { MaterialList: List<Material> ->
            val planList = model.Plans.value!!
            val plan = planList[pos]
            val textfragment = LoaderFragment.newInstance(
                MaterialList as ArrayList<Material>?,
                plan.zuphrLpname,
                plan.zuphrVesselName,
                plan.zuphrCaptain

            )
            var fm = supportFragmentManager
            var ft = fm.beginTransaction()
            ft.replace(R.id.item_recycler, textfragment)
            ft.commit()
        })
    }




    fun Offline_items_PopUpView(vehassignloader: List<VechAssignLoader?>?) {

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.offline_items_vehassignloader_view, main_layout, false)
        val ChangeStatusAll = view.findViewById<Button>(R.id.ChangeStatusAll)
        val DeleteAll = view.findViewById<Button>(R.id.ClearAllLoader)
        val Close = view.findViewById<ImageView>(R.id.closeBtn)
        val recyclerView: RecyclerView = view.findViewById(R.id.items_list)


        Log.i("list size:", vehassignloader?.size.toString() + "")

        val items_adapter = Offline_Items_Loader_adapter(
            vehassignloader,
            model.plan.value?.planToItems,
            this
        )
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(linearLayoutManager)
        recyclerView.setAdapter(items_adapter)


        val popupWindow: PopupWindow
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y
        popupWindow = PopupWindow(view)
        popupWindow.width = width - 100
        popupWindow.height = height - 300
        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.showAtLocation(main_layout, Gravity.CENTER, 0, 0)



        ChangeStatusAll.setOnClickListener { v: View? ->
            popupWindow.dismiss()
            model.VechAssignLoaderList.setValue(java.util.ArrayList())
            model.PostOfflineLoaderContent(vehassignloader, this)
        }


        DeleteAll.setOnClickListener { v: View? ->

            popupWindow.dismiss()

              model.deleteLoaderObject(null,vehassignloader)



        }


        Close.setOnClickListener { v: View? ->
            popupWindow.dismiss()
        }
    }


    override fun updatelist(
        items: MutableList<VechAssignLoader>?,
        vechAssignLoader: VechAssignLoader?
    ) {
        model.deleteLoaderObject(vechAssignLoader,null)

    }

}


