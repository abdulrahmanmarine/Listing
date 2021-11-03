package com.example.listing.Utils;


import com.example.listing.models.Driver;
import com.example.listing.models.LoadAction;
import com.example.listing.models.Plan;
import com.example.listing.models.SAPNote;
import com.example.listing.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class DataClass {
    private static com.example.listing.Utils.DataClass instance;

    List<Plan> plans=new ArrayList<>();
    List<SAPNote> notes=new ArrayList<>();
    Boolean flag_dispatch=false;

    public static DataClass getInstance() {
        return instance;
    }

    public Boolean getFlag_dispatch() {
        return flag_dispatch;
    }

    public void setFlag_dispatch(Boolean flag_dispatch) {
        this.flag_dispatch = flag_dispatch;
    }

    public DataClass() {

        List<com.example.listing.models.Material> mats = new ArrayList<>();
        List<com.example.listing.models.Material> mats2 = new ArrayList<>();

        List<Driver> drivers = new ArrayList<>();
        List<Driver> drivers2 = new ArrayList<>();

        List<Vehicle> vehicles = new ArrayList<>();
        List<Vehicle> vehicles2 = new ArrayList<>();
        List<Vehicle> vehicles3 = new ArrayList<>();


        LoadAction load = new LoadAction("99001", "2021", "1", "5",
                "STEV", "100.000", "EA", false,"2021-08-24", "16:13:13", "AWAMAS0F", "size", "confirmedquan",
                "zuphract", drivers, "weight", vehicles, "LFMS", "content");

        LoadAction load2 = new LoadAction("99002", "2021", "1", "5",
                "STEV", "100", "EA", false,"2021-08-24", "16:13:13", "AWAMAS0F", "size", "confirmedquan",
                "zuphract", drivers2, "weight", vehicles2, "status", "content");

        LoadAction load3 = new LoadAction("99002", "2021", "1", "5",
                "STEV", "100", "EA", false,"2021-08-24", "16:13:13", "AWAMAS0F", "size", "confirmedquan",
                "zuphract", drivers2, "weight", vehicles3, "status", "content");




        mats.add(new com.example.listing.models.Material("5.000", "",  load, "", "",
                "data:image/jpeg;base64,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",
                "120.000", "60.000", "99001",
                false, "","STAG AREA", "",
                "0002", "80.000", "", "ADC-21",
                "CM", "2021", "0001", "2000000101",
                "","1000000100", "0001", "OCTG DRILL PIPE",
                "5CT OCTG Casing Pipe for Testing", "100.000", "EA", "ADC-21",
                "DRW", "LFMS", "DRILL BIT", false,
                "String zuphrStatdt", "", null, "00",
                "", "00", "", "",
                "", null, "PT00H00M00S", "",
                "", "", "", "",
                "", "", "","",
                "", "", "String zuphrFpDate", "PT16H13M13S",
                "AWAMAS0F", drivers, vehicles));

        mats.add(new com.example.listing.models.Material("10.000", "",  load2, "", "",
                "data:image/jpeg;base64,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",
                "120.000", "40.000", "99001",
                false, "","STAG", "",
                "0003", "80.000", "", "ADC-12",
                "String zuphrVolmeins", "2021", "0006", "2000000111",
                "","1000000163", "0002", "GOWE 8 2 INCH",
                "GOWE 8 2 INCH Testing", "1.000", "EA", "",
                "", "RELE", "DRILL BIT", false,
                null, "", null, "00",
                "", "", "", "",
                "MA041996", "String zuphrActdate", "PT09H58M03S", "ABSF-0040",
                "", "", "", "",
                "", "", ""," ",
                "", "", "String zuphrFpDate", "PT16H13M13S",
                "AWAMAS0F", drivers2, vehicles2));

        mats2.add(new com.example.listing.models.Material("10.000", "",  load3, "", "",
                "data:image/jpeg;base64,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",
                "120.000", "40.000", "99001",
                false, "","STAG", "",
                "0003", "80.000", "", "ADC-12",
                "String zuphrVolmeins", "2021", "0006", "2000000111",
                "","1000000163", "0002", "GOWE 8 2 INCH",
                "GOWE 8 2 INCH Testing", "1.000", "EA", "",
                "", "RELE", "API 6A WELLHEAD 2-1", false,
                null, "", null, "00",
                "", "", "", "",
                "MA041996", "String zuphrActdate", "PT09H58M03S", "ABSF-0040",
                "", "", "", "",
                "", "", ""," ",
                "", "", "String zuphrFpDate", "PT16H13M13S",
                "AWAMAS0F", drivers2, vehicles3));




        plans.add(new Plan("LOAD", false, "", "RELE",
                "ZAMIL-403", "HUSSAIN AWAMI", "", "99001",
                "2021","ADC-21 - ADC-12", "DRW", "LOGS", "String zuphrLpdate",
                "PT12H39M38S", "AWAAMIHA", "40023730", "9660140",
                true, false, "String zuphrFpDate", "PT16H13M13S",
                "AWAMAS0F", mats));

        plans.add(new Plan("LOAD", false, "", "RELE",
                "AL GOSAIBI 51", "", "", "99004",
                "2021"," ADC-12", "DRW", "LOGS", "String zuphrLpdate",
                "PT14H21M29S", "AWAMAS0F", "", "9561136",
                false, false, "String zuphrFpDate", "PT09H54M39S",
                "AWAMAS0F", mats2));


    }

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }

    public List<SAPNote> getNotes() {
        return notes;
    }

    public void setNotes(List<SAPNote> notes) {
        this.notes = notes;
    }

    public static void initializer(){
        if(instance == null){
            synchronized (com.example.listing.DataViewModel.Flag.class){
                instance = new com.example.listing.Utils.DataClass();
            }
        }



    }


}
