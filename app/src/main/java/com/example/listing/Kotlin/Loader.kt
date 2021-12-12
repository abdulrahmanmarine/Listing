package com.example.listing.Kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.listing.DataViewModel.Flag
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.Login
import com.example.listing.Material.Loader.LoaderFragment
import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import com.example.listing.Utils.Loginsession
import com.example.listing.ViewModelsFactory.PlansDataModelFactory
import com.example.listing.models.Material
import com.example.listing.models.Plan
import java.util.*

class Loader : AppCompatActivity(), PlanClickListener {
    var reqs = ArrayList<Plan?>()
    lateinit var model: PlansDataModel
    var po = 0
    lateinit var  progressBar: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        this.getSupportActionBar()!!.hide()
       // var logout: Button =findViewById (R.id.homelogout_button)
        progressBar = findViewById(R.id.progressBarlayout)

        model = ViewModelProvider(this, PlansDataModelFactory(this.application)).get(PlansDataModel::class.java)
        model.getplansLoader(application,this)
        model.UserRule.value=true
        progressBar.visibility = View.VISIBLE
        Flag.initializer(true, true);
        Flag.getInstance().planFlag = true;
        Flag.getInstance().materialFlag=true;

        var ctx = applicationContext

        model.Plans.observe(this,
                { Plans: List<Plan?>? ->
                    progressBar.visibility = View.GONE
                    buildRecycler(
                            (Plans as ArrayList<Plan?>?)!!
                    )
                })

//        logout.setOnClickListener {
//            Loginsession.getInstance().user=null
//            var intent = Intent(applicationContext, Login::class.java)
//            startActivity(intent)
//        }


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
}


