package com.example.listing.Kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listing.Material.Loader.LoaderFragment
import com.example.listing.Material.Material
import com.example.listing.Plan.Plan
import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import org.json.JSONObject
import java.util.*

class Loader : AppCompatActivity(), PlanClickListener, PlanFragment.LoaderFragmentClickListener {
    var reqs = ArrayList<Plan?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        this.getSupportActionBar()!!.hide()


        var planData = CommonModule.openRawFileAsIS(this, "plans")
        var resp = ""
        var file = planData.reader().readLines()
        file.forEach { it ->
            resp += it + "\n"
        }

        if (resp != null) {
            var jsonObj = JSONObject(resp)
            var data: JSONObject = jsonObj.getJSONObject("d")
            var results = data.getJSONArray("results")
                for (i in 0 until results.length()) {
                    var mats = ArrayList<Material>()
                    var res = results.getJSONObject(i)
                    var stat = res.getString("ZuphrStatus")
                    var rq_name = res.getString("ZuphrLpid")
                    var time = res.getString("ZuphrLptime")
                    var date = res.getString("ZuphrLpdate")
                    var vessel = res.getString("ZuphrVessel")
                    var driver = ""
                    var materObj = res.getJSONObject("PlanToItems")
                    var mater = materObj.getJSONArray("results")
                    for (j in 0 until mater.length()) {
                        var mat = mater.getJSONObject(j)
                        var mater_name = mat.getString("ZuphrShortxt")
                        var mater_quan = mat.getString("ZuphrQuan")
                        var mater_driver = ""
                        var mater_vehicle = ""
                        mats.add(
                            Material(
                                mater_name,
                                mater_quan,
                                false,
                                mater_driver,
                                mater_vehicle,
                                false
                            )
                        )
                    }
                    reqs.add(Plan(stat, "Plan# $rq_name", date, time, vessel, driver, mats))
                }

        }

        buildRecycler(reqs)


//        var planData = CommonModule.openRawFileAsIS(this, "plans")
//        var planLists = CommonModule.mapper.readValue<List<Plan>>(planData)
//        var plan_adapter = LP_Header_Adapter(null, planLists)
//        var planRV : RecyclerView = findViewById(R.id.plan_recycler)
//        planRV.adapter = plan_adapter


    }

    fun buildRecycler(lst: ArrayList<Plan?>?) {
        var planFragment = PlanFragment.newInstance(lst)
        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        ft.replace(R.id.constraintLayout4, planFragment)
        ft.commit()


    }




    override fun onItemClick(plan: Plan?, pos: Int) {
        LoaderFragmentInteraction(reqs[pos]!!)
        //Toast.makeText(this,pos,Toast.LENGTH_SHORT).show()
    }

    override fun LoaderFragmentInteraction(plan: Plan) {
        var textfragment = LoaderFragment.newInstance(
            plan.materials as ArrayList<Material?>,
            plan.req_name,
            plan.vessel_num,
            plan.destination
        )

        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        ft.replace(R.id.item_recycler, textfragment)
        ft.commit()

//
//        var itemRV : RecyclerView= findViewById(R.id.item_recycler)
//        var itemAdapter = MaterialAdapter(plan.materials)
//        itemRV.layoutManager = GridLayoutManager(this, 3)


//        itemRV.adapter = itemAdapter
    }




}