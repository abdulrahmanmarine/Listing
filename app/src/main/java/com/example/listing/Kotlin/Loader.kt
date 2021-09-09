package com.example.listing.Kotlin

import android.os.Bundle
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
                        var mater_id = mat.getString("ZuphrMblpo")
                        var mater_name = mat.getString("ZuphrShortxt")
                        var mater_quan = mat.getString("ZuphrQuan")
                        var mater_base64 = mat.getString("ZuphrMattype")
                        var mater_driver = ""
                        var mater_vehicle = ""
                        mats.add(
                            Material(
                                mater_id.toInt(),
                                mater_name,
                                mater_quan,
                                mater_base64,
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
            plan.destination,

        )

        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        ft.replace(R.id.item_recycler, textfragment)
        ft.commit()


    }




}