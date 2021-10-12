package com.example.listing.Kotlin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.listing.DataViewModel.Flag
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.DataViewModel.PlansDataModelFactory
import com.example.listing.Material.Loader.LoaderFragment

import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import com.example.listing.models.Material
import com.example.listing.models.Plan
import org.json.JSONObject
import java.util.*

class Loader : AppCompatActivity(), PlanClickListener {
    var reqs = ArrayList<Plan?>()
    lateinit var model: PlansDataModel
    var po = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        this.getSupportActionBar()!!.hide()


        model = ViewModelProvider(this, PlansDataModelFactory(this.application)).get(
            PlansDataModel::class.java
        )
        model.getplans(application)
        Flag.initializer(true, true);
        Flag.getInstance().planFlag = true;
        Flag.getInstance().materialFlag=true;

        var ctx = applicationContext

        model.Plans.observe(this,
            { Plans: List<Plan?>? ->
                buildRecycler((Plans as ArrayList<Plan?>?)!!
                )
            })

    }

    fun buildRecycler(lst: ArrayList<Plan?>) {
        var planFragment = PlanFragment.newInstance(lst, true)
        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        ft.replace(R.id.constraintLayout4, planFragment)
        ft.commit()
    }


//    override fun onItemClick(plan: Plan?, pos: Int) {
//        if (plan != null) {
//            val sie = plan.planToItems.size
//            Log.i("mateials in","" + sie);
//        };
//        LoaderFragmentInteraction(reqs[pos]!!)
//        //Toast.makeText(this,pos,Toast.LENGTH_SHORT).show()
//    }

    override fun onItemClick(plan: Plan?, pos: Int) {
        model.plan.value = plan
        LoaderFragmentInteraction(plan!!, pos)


        // LoaderFragmentInteraction(reqs[pos]!!)
//        model = ViewModelProviders.of(active)
//
//        model.Plans.value?.let { LoaderFragmentInteraction(it[pos]) }

        //Toast.makeText(this,pos,Toast.LENGTH_SHORT).show()
    }

    fun LoaderFragmentInteraction(plan: Plan, pos: Int) {
        model.plan.observe(this, { plan: Plan? ->
            val planList = model.Plans.value!!
            planList[pos] = plan
            model.Plans.value = planList
            model.MatrialsList.value = plan?.planToItems

        })

        model.MatrialsList.observe(this, { MaterialList: List<Material> ->

            val textfragment = LoaderFragment.newInstance(
                MaterialList as ArrayList<Material>?,
//            plan.req_name,
//            plan.vessel_num,
//            plan.destination
                "", "", ""
            )

            var fm = supportFragmentManager
            var ft = fm.beginTransaction()
            ft.replace(R.id.item_recycler, textfragment)
            ft.commit()
        })
    }
}