package com.example.listing

object DatabaseScripts {

    val PlanTable = "Create table IF NOT EXISTS Plans(LPid integer primary key ,plan_type text,planner text," +
            "LP_date TEXT , LP_time text, status text, icon text," +
            " modified integer , vessel text, captain text,limit_warning integer, deletion integer, dbmodified integer); "

    val ItemsTable = "Create table IF NOT EXISTS Items(LPid integer," +
            "LP_item_id integer, reqid text, req_item_id text, 'to' text, sequence integer, matnr integer, desc text, image text, " +
            " qty integer, uom text,status text,manifested integer, shipper text, objection integer, LP_itm_date text, LP_itm_time text, " +
            "fp_name text,fp_date text,fp_time text,deletion integer" +
            ", dbmodified integer, FOREIGN KEY(LPid) REFERENCES Plans(LPid), PRIMARY KEY (LPid, LP_item_id));"

    val ActionsTable = "Create table IF NOT EXISTS Actions(Actorder integer PRIMARY KEY, Action text, Actlabel text, " +
            "Stattype text, RemarksReq text, id text, uri text, type text );"

    val TicketTable = "Create table IF NOT EXISTS Tickets(Type text, Process text, Size text, Weight text," +
            " Comment text, Issued text, LPid text, LP_item_id text, reqid text," +
            "user text, dbstatus integer, TID " +
            "integer primary key autoincrement);"

    val NotesTable = "Create table IF NOT EXISTS Notes(LPid integer, LP_item_id integer," +
            " NID integer primary key autoincrement, noteText text, noteVoice text, user text,voice integer);"

    val UsersTable = "Create table IF NOT EXISTS Users(username text primary key)";

    val FlagsTable = "Create table IF NOT EXISTS Flags(logged1c integer, currentUser text, userType integer, plansLoaded integer)"


}