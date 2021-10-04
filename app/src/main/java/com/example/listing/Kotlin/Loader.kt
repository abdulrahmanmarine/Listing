package com.example.listing.Kotlin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.DataViewModel.PlansDataModelFactory
import com.example.listing.Material.Dispatcher.DispatcherFragment
import com.example.listing.Material.Loader.LoaderFragment
import com.example.listing.Material.Material
import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import com.example.listing.models.Material2
import com.example.listing.models.Plan2
import org.json.JSONObject
import java.util.*

class Loader : AppCompatActivity(), PlanClickListener {
    var reqs = ArrayList<Plan2?>()
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
        var ctx = applicationContext

        model.Plans.observe(this,
            { Plans: List<Plan2?>? ->
                buildRecycler((Plans as ArrayList<Plan2?>?)!!
                )
            })

    }

    fun buildRecycler(lst: ArrayList<Plan2?>) {
        var planFragment = PlanFragment.newInstance(lst)
        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        ft.replace(R.id.constraintLayout4, planFragment)
        ft.commit()
    }


//    override fun onItemClick(plan: Plan2?, pos: Int) {
//        if (plan != null) {
//            val sie = plan.planToItems.size
//            Log.i("mateials in","" + sie);
//        };
//        LoaderFragmentInteraction(reqs[pos]!!)
//        //Toast.makeText(this,pos,Toast.LENGTH_SHORT).show()
//    }

    override fun onItemClick(plan: Plan2?, pos: Int) {
        model.plan.value = plan
        LoaderFragmentInteraction(plan!!, pos)


        // LoaderFragmentInteraction(reqs[pos]!!)
//        model = ViewModelProviders.of(active)
//
//        model.Plans.value?.let { LoaderFragmentInteraction(it[pos]) }

        //Toast.makeText(this,pos,Toast.LENGTH_SHORT).show()
    }

    fun LoaderFragmentInteraction(plan: Plan2, pos: Int) {

        model.plan.observe(this, { plan: Plan2? ->
            val planList = model.Plans.value!!
            planList[pos] = plan
            model.Plans.value = planList
            model.MatrialsList.value = plan?.planToItems
        })

        model.MatrialsList.observe(this, { MaterialList: List<Material2> ->

            val textfragment = LoaderFragment.newInstance(
                MaterialList as ArrayList<Material2>?,
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