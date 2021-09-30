package com.example.listing.Kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.listing.AssignDriver.AssignDialogFragment
import com.example.listing.AssignDriver.AssignDialogFragment.OnNegativeClickListener
import com.example.listing.AssignDriver.AssignDialogFragment.OnPositiveClickListener
import com.example.listing.AssignDriver.AssignMultiDialogFragment
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.DataViewModel.PlansDataModelFactory
import com.example.listing.Material.Dispatcher.DispatcherFragment
import com.example.listing.Material.Material
import com.example.listing.Plan.Plan
import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import com.example.listing.models.Driver
import com.example.listing.models.Material2
import com.example.listing.models.Plan2
import com.example.listing.models.Vehicle
import org.json.JSONObject
import java.util.*

class Dispatcher : AppCompatActivity(), PlanClickListener, PlanFragment.LoaderFragmentClickListener, OnPositiveClickListener, OnNegativeClickListener{

    var dialog: DialogFragment? = null
    var materialpos = 0
    var po = 0
    val reqs = ArrayList<Plan2?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        this.getSupportActionBar()!!.hide()

        var model = ViewModelProvider(this, PlansDataModelFactory(this.application)).get(
            PlansDataModel::class.java
        )
        model.getplans(application)
        var ctx = applicationContext

        model.Plans.observe(this,
            { Plans: List<Plan2?>? ->
                buildRecycler((Plans as ArrayList<Plan2?>?)!!
                )
            })
    }

    fun buildRecycler(lst: ArrayList<Plan2?>?) {
        val planFragment = PlanFragment.newInstance(lst)
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.constraintLayout4, planFragment)
        ft.commit()
    }


    override fun onItemClick(plan: Plan2?, pos: Int) {
        LoaderFragmentInteraction(plan!!)
        po = pos
    }
//    override fun onItemClick(pos: Int) {
//        LoaderFragmentInteraction(reqs[pos]!!)
//        po = pos
//    }

    override fun LoaderFragmentInteraction(plan: Plan2) {
        val textfragment = DispatcherFragment.newInstance(
            plan.planToItems as ArrayList<Material>,
//            plan.req_name,
//            plan.vessel_num,
//            plan.destination
        "","",""
        )
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.item_recycler, textfragment)
        ft.commit()

//
//        var itemRV : RecyclerView= findViewById(R.id.item_recycler)
//        var itemAdapter = MaterialAdapter(plan.materials)
//        itemRV.layoutManager = GridLayoutManager(this, 3)


//        itemRV.adapter = itemAdapter
    }

    fun showAssignDialog(matpos: Int, material: Material2) {



        materialpos = matpos
        val fra = supportFragmentManager
//        dialog = AssignMultiDialogFragment.newInstance(reqs[po]!!.materials[matpos].name,
//            reqs[po]!!.materials[matpos].material, driverList, vehicleList, pairList)
        dialog = AssignMultiDialogFragment.newInstance(material);

//        dialog = AssignMultiDialogFragment.newInstance(reqs[po]!!.planToItems[matpos].zuphrFpName,
//            reqs[po]!!.planToItems[matpos].zuphrActquan, reqs[po]!!.planToItems[matpos].drivers as ArrayList<Driver>?
//        );
        //dialog = AssignDialogFragment.newInstance(reqs[po]!!.materials[matpos].name,reqs[po]!!.materials[matpos].material)

//        (dialog as AssignDialogFragment?)!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
//        (dialog as AssignDialogFragment?)!!.show(fra, "assign")

        (dialog as AssignMultiDialogFragment?)!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
        (dialog as AssignMultiDialogFragment?)!!.show(fra, "assign")


//        materialpos = matpos
//        val fra = supportFragmentManager
//        //dialog = AssignDialogFragment.newInstance(reqs[po]!!.materials[matpos].name,reqs[po]!!.materials[matpos].material)
//        dialog = AssignDialogFragment.newInstance(reqs[po]!!.planToItems[matpos].zuphrFpName, reqs[po]!!.planToItems[matpos].zuphrFpName)
//        (dialog as AssignDialogFragment?)!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
//        (dialog as AssignDialogFragment?)!!.show(fra, "assign")
    }
//    override fun onPositiveClick(text: String?, text2: String?) {
//        reqs[po]!!.materials[materialpos].driver = text
//        reqs[po]!!.materials[materialpos].vehicle = text2
//        dialog!!.dismiss()
//    }

    override fun onPositiveClick(text: String?, text2: String?) {
        reqs[po]!!.planToItems[materialpos].drivers[0].zuphrdrvrName = text

//        reqs[po]!!.planToItems[materialpos].driver.zuphrdrvrName = text
//        reqs[po]!!.planToItems[materialpos].vehicle.vehType= text2
        dialog!!.dismiss()
    }

    override fun onNegativeClick() {
        //  dialog.dismiss();
    }


}