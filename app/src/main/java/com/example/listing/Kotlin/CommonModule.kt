package com.example.listing.Kotlin

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

object CommonModule {
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

    fun openRawFileAsIS(ctx: Context, filename:String) : InputStream {
        return ctx.resources.openRawResource(ctx.resources.getIdentifier(filename,
            "raw", ctx.getPackageName()))


    }

    fun parseTime(str : String):String? {
        return if(!str.isNullOrBlank()) {
            var hours = str.substring(str.indexOf("H") - 2, str.indexOf("H")).toInt()
            var mins = str.substring(str.indexOf("M") - 2, str.indexOf("M")).toInt()
            var secs = str.substring(str.indexOf("S") - 2, str.indexOf("S")).toInt()
            "$hours:$mins:$secs"
        } else
            null
    }

    fun parseDate(str : String):String? {
        Log.d("STR TAG", str)
        return if(!str.isNullOrBlank() && str != "null") {
            var newstr = str.substring(str.indexOf("(") + 1, str.indexOf(")"))
            SimpleDateFormat("dd-MM-yyyy").format(Date(newstr.toLong()))
        } else
            null
    }



}