package com.example.listing.DataViewModel;

import android.util.Log;

public class Flag {
    private static Flag instance;
    private Boolean planFlag = true;
    private Boolean materialFlag = true;


    public Flag(Boolean planFlag, Boolean materialFlag) {
        this.planFlag = planFlag;
        this.materialFlag = materialFlag;
    }

    public static void setInstance(Flag instance) {
        Flag.instance = instance;
    }

    public static void initializer(Boolean planFlag, Boolean materialFlag){
        if(instance == null){
            synchronized (Flag.class){
                instance = new Flag(true, true);
            }
        }

    }

    public static Flag getInstance(){
        if(instance == null){
            return null;
        }else{
            Log.i("response","here");
            return instance;
        }
    }

    public Boolean getPlanFlag() {
        return planFlag;
    }

    public void setPlanFlag(Boolean planFlag) {
        this.planFlag = planFlag;
    }

    public Boolean getMaterialFlag() {
        return materialFlag;
    }

    public void setMaterialFlag(Boolean materialFlag) {
        this.materialFlag = materialFlag;
    }
}
