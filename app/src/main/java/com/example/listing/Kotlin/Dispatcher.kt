package com.example.listing.Kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider

import com.example.listing.AssignDialog_Configured.Configured_AssignMultiDialogFragment
import com.example.listing.AssignDialog_Configured.ChosenDriverCardAdapter
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.ViewModelsFactory.PlansDataModelFactory
import com.example.listing.Material.Dispatcher.DispatcherFragment
import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import com.example.listing.models.Material
import com.example.listing.models.Plan
import java.util.*

class Dispatcher : AppCompatActivity(), PlanClickListener, PlanFragment.LoaderFragmentClickListener{

    var dialog: DialogFragment? = null
    lateinit var chosenDriverCardAdapter: ChosenDriverCardAdapter;
    var materialpos = 0
    var po = 0
    lateinit var model :PlansDataModel
    val reqs = ArrayList<Plan?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        this.getSupportActionBar()!!.hide()

         model = ViewModelProvider(this,
             PlansDataModelFactory(this.application)
         ).get(
            PlansDataModel::class.java
        )
        model.getplans(application)
        var ctx = applicationContext

        model.Plans.observe(this,
            { Plans: List<Plan?>? ->
                for (i in 0..1) {
                    if (Plans != null) {
                        Log.i("for test", Plans.get(i)!!.zuphrVessel + "")
                    }
            }
                buildRecycler((Plans as ArrayList<Plan?>?)!!


                )
            })
    }

    fun buildRecycler(lst: ArrayList<Plan?>?) {


        val planFragment = PlanFragment.newInstance(lst, true);
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.constraintLayout4, planFragment)
        ft.commit()
    }


    override fun onItemClick(plan: Plan?, pos: Int) {
        model.plan.value = plan
        LoaderFragmentInteraction(pos)
        po = pos
    }
//    override fun onItemClick(pos: Int) {
//        LoaderFragmentInteraction(reqs[pos]!!)
//        po = pos
//    }

    override fun LoaderFragmentInteraction( pos: Int) {

        model.plan.observe(this, {plan: Plan? ->
            val planList = model.Plans.value!!
            planList[pos] = plan
            model.Plans.value = planList
            model.MatrialsList.value=plan?.planToItems
        })

        model.MatrialsList.observe(this,{MaterialList:List<Material> ->

            val textfragment = DispatcherFragment.newInstance(
                MaterialList as ArrayList<Material>?,
//            plan.req_name,
//            plan.vessel_num,
//            plan.destination
                "","",""
            )
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(R.id.item_recycler, textfragment)
            ft.commit()

        })




//
//        var itemRV : RecyclerView= findViewById(R.id.item_recycler)
//        var itemAdapter = MaterialAdapter(plan.materials)
//        itemRV.layoutManager = GridLayoutManager(this, 3)


//        itemRV.adapter = itemAdapter
    }

    fun showAssignDialog(
        matpos: Int,
        material: Material,
    ) {
        materialpos = matpos
        val fra = supportFragmentManager
//        dialog = AssignMultiDialogFragment.newInstance(reqs[po]!!.materials[matpos].name,
//            reqs[po]!!.materials[matpos].material, driverList, vehicleList, pairList)
        dialog = Configured_AssignMultiDialogFragment.newInstance(matpos,material);

//        dialog = AssignMultiDialogFragment.newInstance(reqs[po]!!.planToItems[matpos].zuphrFpName,
//            reqs[po]!!.planToItems[matpos].zuphrActquan, reqs[po]!!.planToItems[matpos].drivers as ArrayList<Driver>?
//        );
        //dialog = AssignDialogFragment.newInstance(reqs[po]!!.materials[matpos].name,reqs[po]!!.materials[matpos].material)

//        (dialog as AssignDialogFragment?)!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
//        (dialog as AssignDialogFragment?)!!.show(fra, "assign")

        (dialog as Configured_AssignMultiDialogFragment?)!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
        (dialog as Configured_AssignMultiDialogFragment?)!!.show(fra, "assign")


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



}