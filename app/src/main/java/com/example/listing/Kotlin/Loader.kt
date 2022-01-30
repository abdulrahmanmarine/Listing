package com.example.listing.Kotlin

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        lateinit var logout : FloatingActionButton

        progressBar = findViewById(R.id.progressBarlayout)

        val android_id = Settings.Secure.getString(this.getContentResolver(),
            Settings.Secure.ANDROID_ID
        )

        model = ViewModelProvider(this, PlansDataModelFactory(this.application,android_id)).get(PlansDataModel::class.java)
        model.getplansLoader( this)
        model.UserRule.value=true
        progressBar.visibility = View.VISIBLE
        Flag.initializer(true, true);
        Flag.getInstance().planFlag = true;
        Flag.getInstance().materialFlag=true;
        logout=findViewById(R.id.logout_button);

        model.Plans.observe(this,
            { Plans: List<Plan?>? ->
                progressBar.visibility = View.GONE
                buildRecycler(
                    (Plans as ArrayList<Plan?>?)!!
                )
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
        ft.replace(R.id.constraintLayout4, planFragment)
        ft.commit()
    }




    override fun onItemClick(plan: Plan?, pos: Int) {
        model.plan.value = plan
        LoaderFragmentInteraction(plan!!, pos)
        model.getLoaderMtr(plan?.ZuphrLpid,pos)




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


