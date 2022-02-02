package com.example.listing.notes

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

object SharefPref {
    val mapper = jacksonObjectMapper()
    fun saveJson(context: Context, filename: String, json: String) {

        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("json", json).apply()
    }

    fun loadJson(context: Context, filename: String): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        return sharedPreferences.getString("json", "")
    }

    fun openRawFileAsIS(ctx: Context, filename: String): InputStream {
        return ctx.resources.openRawResource(
            ctx.resources.getIdentifier(
                filename,
                "raw", ctx.getPackageName()
            )
        )


    }


    fun parseTime(str: String): String? {
        return if (!str.isNullOrBlank()) {
            var hours = str.substring(str.indexOf("H") - 2, str.indexOf("H"))
            var mins = str.substring(str.indexOf("M") - 2, str.indexOf("M"))
            var secs = str.substring(str.indexOf("S") - 2, str.indexOf("S"))
            var morining = "AM"
            if(hours.toInt() > 12 ){
                if(hours.toInt() != 12)
                    hours = (hours.toInt() - 12).toString()
                morining = "PM" }
            "$hours:$mins $morining"
        } else
            null
    }

    fun parseDate(str: String): String? {
        Log.d("STR TAG", str)
        return if (!str.isNullOrBlank() && str != "null") {
            var newstr = str.substring(str.indexOf("(") + 1, str.indexOf(")"))
            SimpleDateFormat("dd-MM-yyyy").format(Date(newstr.toLong()))
        } else
            null
    }


    fun parseTimetoSAP(str: String): String? {
        if(!str.isNullOrBlank())
        {
            var strArray = str.split(":")
           when(strArray.size){
               0->return null
               1->return  "PT"+strArray[0]+"H00M00S"
               2->return  "PT"+strArray[0]+"H"+strArray[1]+"M00S"
               3->return  "PT"+strArray[0]+"H"+strArray[1]+"M"+strArray[2]+"S"

           }

        }
        return null
    }

    fun parseDatetoSAP(str: String): String? {
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return "\\/Date(" + df.parse(str).time.toString() +")"
    }
}

//ZuphrLpdate": "\/Date(1622073600000)\/",
//"ZuphrLptime": "PT12H39M38S",