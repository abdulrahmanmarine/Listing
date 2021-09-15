package com.example.listing.Kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.DataViewModel.PlansDataModelFactory
import com.example.listing.Material.Loader.LoaderFragment
import com.example.listing.Material.Material
import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import com.example.listing.models.Material2
import com.example.listing.models.Plan2
import org.json.JSONObject
import java.util.*

class Loader : AppCompatActivity(), PlanClickListener, PlanFragment.LoaderFragmentClickListener {
    var reqs = ArrayList<Plan2?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        this.getSupportActionBar()!!.hide()

        var model = ViewModelProvider(this, PlansDataModelFactory(this.application)).get(
            PlansDataModel::class.java
        )

        model.getplans(application)


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




    override fun onItemClick(plan: Plan2?, pos: Int) {
        LoaderFragmentInteraction(reqs[pos]!!)
        //Toast.makeText(this,pos,Toast.LENGTH_SHORT).show()
    }

    override fun LoaderFragmentInteraction(plan: Plan2) {
        var textfragment = LoaderFragment.newInstance(
            plan.planToItems as ArrayList<Material2?>,
//            plan.req_name,
//            plan.vessel_num,
//            plan.destination,
            "","",""

        )

        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        ft.replace(R.id.item_recycler, textfragment)
        ft.commit()


    }




}