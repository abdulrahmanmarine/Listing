package com.example.listing.Kotlin

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listing.*
import com.example.listing.AssignDialog_Configured.Configured_AssignMultiDialogFragment
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.Manual_Assigment.Manual_AssignMultiDialogFragment
import com.example.listing.Material.Dispatcher.DispatcherFragment
import com.example.listing.Plan.PlanFragment
import com.example.listing.Utils.Loginsession
import com.example.listing.ViewModelsFactory.PlansDataModelFactory
import com.example.listing.models.Material
import com.example.listing.models.Plan
import com.example.listing.models.VehAssign
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class Dispatcher : AppCompatActivity(), PlanClickListener, PlanFragment.LoaderFragmentClickListener,
    Offlineitem_updatelist {

    var dialog: androidx.fragment.app.DialogFragment? = null
    var dialogManual: androidx.fragment.app.DialogFragment? = null
    private var flag: Boolean = true
    var materialpos = 0
    var po = 0
    lateinit var progressBarContainer: LinearLayout
    lateinit var progressBarText: TextView
    lateinit var progressBar: ProgressBar
    lateinit var model: PlansDataModel
    lateinit var logout: FloatingActionButton
    var main_layout: ConstraintLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        this.supportActionBar!!.hide()

        progressBarContainer = findViewById(R.id.progressBarlayout)
        progressBar = findViewById(R.id.progressBar)
        progressBarText = findViewById(R.id.progressbartext)
        model = ViewModelProvider(this, PlansDataModelFactory(this.application, "")).get(
                PlansDataModel::class.java
        )
        model.getplansDispatcher(application, this)

        model.UserRule.value = false
        logout = findViewById(R.id.logout_button)



        main_layout = findViewById(R.id.mainlayout)

        model.Plans.observe(this, { Plans: List<Plan?>? ->

            progressBar.visibility = View.GONE
            if (Plans != null) {
                if (Plans!!.size > 0) {
                    progressBarContainer.visibility = View.GONE
                    buildRecycler((Plans as ArrayList<Plan?>?)!!)
                } else {
                    progressBarText.setText("No Plans found")
                }
            }else {
                progressBarText.setText("No Plans found")
            }
        })


        logout.setOnClickListener {
            Loginsession.getInstance().user = null
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }


        model.VechAssignDispatchList.observe(this, { AssignList: List<VehAssign?>? ->
            if (AssignList != null) {
                if (AssignList.isNotEmpty()) {
                    Offline_items_PopUpView(AssignList)
                }
            }


        })


    }


    private fun buildRecycler(lst: ArrayList<Plan?>?) {

        val planFragment = PlanFragment.newInstance(lst, true)
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.mainlayout, planFragment)
        ft.commit()
    }


    override fun onItemClick(plan: Plan?, pos: Int) {
        //  model.getDispatchMtr(plan?.ZuphrLpid, this)

        Loginsession.getInstance().setOfflineflag(true)
        model.offlineDispatchItems(this, plan?.ZuphrLpid)
        model.plan.value = plan
        LoaderFragmentInteraction(pos)
        po = pos
    }

    override fun LoaderFragmentInteraction(pos: Int) {

        model.plan.observe(this, { plan: Plan? ->
            model.MatrialsList.value = plan?.planToItems

        })

        model.MatrialsList.observe(this, { MaterialList: List<Material> ->
            val planList = model.Plans.value!!
            val plan = planList[pos]
            val textfragment = DispatcherFragment.newInstance(
                    MaterialList as ArrayList<Material>?,
                    plan.zuphrLpname,
                    plan.zuphrVesselName,
                    plan.zuphrOffshore
            )

            // model.getDevice();
            Log.i("destnation",  plan.zuphrOffshore + "")

            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(R.id.item_recycler, textfragment)
            ft.commit()

        })

    }

    fun showAssignDialog(matpos: Int, material: Material) {
        materialpos = matpos
        val fra = supportFragmentManager

        dialog = Configured_AssignMultiDialogFragment.newInstance(matpos, material)
        dialogManual = Manual_AssignMultiDialogFragment.newInstance(matpos, material)

        (dialog as Configured_AssignMultiDialogFragment?)!!.setStyle(
                androidx.fragment.app.DialogFragment.STYLE_NORMAL,
                R.style.CustomDialog
        )
        (dialogManual as Manual_AssignMultiDialogFragment?)!!.setStyle(
                androidx.fragment.app.DialogFragment.STYLE_NORMAL,
                R.style.CustomDialog
        )

        if (!flag) {
            (dialog as Configured_AssignMultiDialogFragment?)!!.show(fra, "assign")
        } else {
            (dialogManual as Manual_AssignMultiDialogFragment?)!!.show(fra, "assign")
        }


    }


    fun Offline_items_PopUpView(vehAssigns: List<VehAssign?>?) {

        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.offline_items_vehassign_view, main_layout, false)
        val DispatchAll = view.findViewById<Button>(R.id.DispatchAll)
        val Close = view.findViewById<ImageView>(R.id.closeBtn)
        val recyclerView: RecyclerView = view.findViewById(R.id.items_list)


        val items_adapter = Offline_Items_Dispatch_adapter(
                vehAssigns,
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



        DispatchAll.setOnClickListener { v: View? ->
            popupWindow.dismiss()
            model.VechAssignDispatchList.setValue(ArrayList<VehAssign>())
            model.PostOfflineDispatchContent(vehAssigns, this)
        }



        Close.setOnClickListener { v: View? ->
            popupWindow.dismiss()
        }
    }

    override fun updatelist(items: MutableList<VehAssign>?, vehAssign: VehAssign?) {
        model.deleteDispatchObject(vehAssign)

    }


}