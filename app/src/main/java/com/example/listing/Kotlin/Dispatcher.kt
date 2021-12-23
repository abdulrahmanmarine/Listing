package com.example.listing.Kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.listing.AssignDialog_Configured.Configured_AssignMultiDialogFragment
import com.example.listing.DataViewModel.PlansDataModel
import com.example.listing.Login
import com.example.listing.Manual_Assigment.Manual_AssignMultiDialogFragment
import com.example.listing.Material.Dispatcher.DispatcherFragment
import com.example.listing.Plan.PlanFragment
import com.example.listing.PlanClickListener
import com.example.listing.R
import com.example.listing.Utils.DataClass
import com.example.listing.Utils.Loginsession
import com.example.listing.ViewModelsFactory.PlansDataModelFactory
import com.example.listing.models.Material
import com.example.listing.models.Plan
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class Dispatcher : AppCompatActivity(), PlanClickListener, PlanFragment.LoaderFragmentClickListener{

    var dialog: DialogFragment? = null
    var dialogManual: DialogFragment? = null
    private var flag: Boolean = true
    var materialpos = 0
    var po = 0
    lateinit var model :PlansDataModel
    lateinit var logout :FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)
        this.supportActionBar!!.hide()

        model = ViewModelProvider(this, PlansDataModelFactory(this.application,"")).get(PlansDataModel::class.java)
        model.getplansDispatcher(application,this)
        model.UserRule.value=false
        logout=findViewById(R.id.logout_button)

        model.Plans.observe(this, { Plans: List<Plan?>? ->
                for (i in 0..1) {
                    if (Plans != null) {
                        Log.i("for test", Plans[i]!!.zuphrVessel + "")
                    }
            }
                buildRecycler((Plans as ArrayList<Plan?>?)!!


                )
            })


        logout.setOnClickListener {
           // Loginsession.getInstance().user=null
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }


    }


    private fun buildRecycler(lst: ArrayList<Plan?>?) {

        val planFragment = PlanFragment.newInstance(lst, true)
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.constraintLayout4, planFragment)
        ft.commit()
    }


    override fun onItemClick(plan: Plan?, pos: Int) {

        model.getDispatchMtr(plan?.ZuphrLpid)

        model.plan.value = plan
        LoaderFragmentInteraction(pos)
        po = pos
    }

    override fun LoaderFragmentInteraction( pos: Int) {

        model.plan.observe(this, {plan: Plan? ->
            model.MatrialsList.value=plan?.planToItems
        })

        model.MatrialsList.observe(this,{MaterialList:List<Material> ->
            val planList = model.Plans.value!!
            val plan = planList[pos]
            val textfragment = DispatcherFragment.newInstance(
                MaterialList as ArrayList<Material>?,
            plan.zuphrLpname,
            plan.zuphrVesselName,
            plan.zuphrCaptain
            )
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            ft.replace(R.id.item_recycler, textfragment)
            ft.commit()

        })

    }

    fun showAssignDialog(matpos: Int, material: Material) {
        materialpos = matpos
        val fra = supportFragmentManager

        dialog = Configured_AssignMultiDialogFragment.newInstance(matpos,material)
        dialogManual=Manual_AssignMultiDialogFragment.newInstance(matpos,material)

        (dialog as Configured_AssignMultiDialogFragment?)!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
        (dialogManual as Manual_AssignMultiDialogFragment?)!!.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)

        flag=DataClass.getInstance().flag_dispatch
        if(flag){
           (dialog as Configured_AssignMultiDialogFragment?)!!.show(fra, "assign")
        }else{
            (dialogManual as Manual_AssignMultiDialogFragment?)!!.show(fra, "assign")
        }


    }



}