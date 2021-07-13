package com.example.listing

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.listing.DatabaseScripts

class DBManager(mctx : Context) {
    var dbsql = DatabaseHelper(mctx).writableDatabase
    companion object {
        var DBName = "LPDB"
        var version = 5

        class DatabaseHelper(ctx : Context): SQLiteOpenHelper(ctx, DBName ,null,version ) {
            val ctx = ctx
            override fun onCreate(p0: SQLiteDatabase?) {
                p0!!.execSQL(DatabaseScripts.PlanTable )
                p0!!.execSQL(DatabaseScripts.ItemsTable)
                p0!!.execSQL(DatabaseScripts.ActionsTable)
                p0!!.execSQL(DatabaseScripts.TicketTable)
                p0!!.execSQL(DatabaseScripts.NotesTable)
                p0!!.execSQL(DatabaseScripts.UsersTable)
                p0!!.execSQL(DatabaseScripts.FlagsTable)
                var fconts = ContentValues()
                fconts.put("logged1c",0)
                fconts.put("currentUser","")
                fconts.put("userType",0)
                p0!!.insert("Flags", null, fconts)
                Toast.makeText(ctx, "DATBASE CREATED",Toast.LENGTH_SHORT).show()


            }

            override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
                Toast.makeText(ctx, "DROPPED DATABASE",Toast.LENGTH_SHORT).show()
                p0!!.execSQL("drop table IF EXISTS Plans;")
                p0!!.execSQL("drop table IF EXISTS Items;")
                p0!!.execSQL("drop table IF EXISTS Actions;")
                p0!!.execSQL("drop table IF EXISTS Tickets;")
                p0!!.execSQL("drop table IF EXISTS Notes;")
                p0!!.execSQL("drop table IF EXISTS Users;")
                p0!!.execSQL("drop table IF EXISTS Flags;")

                onCreate(p0)
            }


        }
    }




}
