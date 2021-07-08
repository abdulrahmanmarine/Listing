package com.example.listing.Kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.listing.AssignDriver.AssignDialogFragment
import com.example.listing.AssignDriver.AssignDialogFragment.OnNegativeClickListener
import com.example.listing.AssignDriver.AssignDialogFragment.OnPositiveClickListener
import com.example.listing.Material.Dispatcher.DispatcherFragment
import com.example.listing.Material.Material
import com.example.listing.Plan.Plan
import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import org.json.JSONObject
import java.util.*

class Dispatcher : AppCompatActivity(), PlanClickListener, PlanFragment.LoaderFragmentClickListener, OnPositiveClickListener, OnNegativeClickListener{

    var dialog: DialogFragment? = null
    var materialpos = 0
    var po = 0
    val reqs = ArrayList<Plan?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)

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
                val mats = ArrayList<Material>()

                val res = results.getJSONObject(i)
                val stat = res.getString("ZuphrStatus")
                val rq_name = res.getString("ZuphrLpid")
                val time = res.getString("ZuphrLptime")
                val date = res.getString("ZuphrLpdate")
                val vessel = res.getString("ZuphrVessel")
                val driver = ""
                val materObj = res.getJSONObject("PlanToItems")
                val mater = materObj.getJSONArray("results")
                mats.clear()
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
        po = pos
    }

    override fun LoaderFragmentInteraction(plan: Plan) {
        val textfragment = DispatcherFragment.newInstance(
            plan.materials as ArrayList<Material?>,
            plan.req_name,
            plan.vessel_num,
            plan.destination
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

    fun showAssignDialog(matpos: Int) {
        materialpos = matpos
        val fra = supportFragmentManager
        dialog = AssignDialogFragment.newInstance(reqs[po]!!.materials[matpos].name)
        (dialog as AssignDialogFragment?)!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
        (dialog as AssignDialogFragment?)!!.show(fra, "assign")
    }
    override fun onPositiveClick(text: String?, text2: String?) {
        reqs[po]!!.materials[materialpos].driver = text
        reqs[po]!!.materials[materialpos].vehicle = text2
        dialog!!.dismiss()
    }

    override fun onNegativeClick() {
        //  dialog.dismiss();
    }



}