package com.example.listing.Kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listing.CustomGridRecyclerView
import com.example.listing.Material.Loader.LoaderFragment
import com.example.listing.Material.Material
import com.example.listing.Material.MaterialAdapter
import com.example.listing.Plan.Plan
import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import org.json.JSONObject
import java.util.*

class Loader : AppCompatActivity(), PlanClickListener, PlanFragment.LoaderFragmentClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        val reqs = ArrayList<Plan?>()
        val mats = ArrayList<Material>()
        var planData = CommonModule.openRawFileAsIS(this, "plans")
        var resp = ""
        val file = planData.reader().readLines()
        file.forEach { it ->
            resp += it + "\n"
        }

        if (resp != null) {
                val jsonObj = JSONObject(resp)
                val data: JSONObject = jsonObj.getJSONObject("d")
                val results = data.getJSONArray("results")
                for (i in 0 until results.length()) {
                    val res = results.getJSONObject(i)
                    val stat = res.getString("ZuphrStatus")
                    val rq_name = res.getString("ZuphrLpid")
                    val time = res.getString("ZuphrLptime")
                    val date = res.getString("ZuphrLpdate")
                    val vessel = res.getString("ZuphrVessel")
                    val driver = ""
                    val materObj = res.getJSONObject("PlanToItems")
                    val mater = materObj.getJSONArray("results")
                    for (j in 0 until mater.length()) {
                        val mat = mater.getJSONObject(j)
                        val mater_name = mat.getString("ZuphrShortxt")
                        val mater_quan = mat.getString("ZuphrQuan")
                        val mater_driver = ""
                        val mater_vehicle = ""
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
        val planFragment = PlanFragment.newInstance(lst)
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.constraintLayout4, planFragment)
        ft.commit()


    }




    override fun onItemClick(plan: Plan?, pos: Int) {
        LoaderFragmentInteraction(plan!!)
    }

    override fun LoaderFragmentInteraction(plan: Plan) {
//        val textfragment = LoaderFragment.newInstance(
//            plan.materials as ArrayList<Material?>,
//            plan.req_name,
//            plan.vessel_num,
//            plan.destination
//        )
//        val fm = supportFragmentManager
//        val ft = fm.beginTransaction()
//        ft.replace(R.id.item_recycler, textfragment)
//        ft.commit()

        var itemRV : RecyclerView= findViewById(R.id.item_recycler)
        var itemAdapter = MaterialAdapter(plan.materials)
        itemRV.layoutManager = GridLayoutManager(this, 3)

        itemRV.adapter = itemAdapter
    }

}