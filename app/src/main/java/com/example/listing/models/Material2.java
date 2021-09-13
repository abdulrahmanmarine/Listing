package com.example.listing.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,creatorVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Material2 implements Serializable {


    @JsonProperty("ZuphrActquan")
    String ZuphrActquan;

    @JsonProperty("Log")
    String Log;


   // @JsonProperty("ZuphrLoada")
    String item;
    LoadAction ZuphrLoada;

    @JsonProperty("ZuphrLoada")
    public void unpackd(String d) throws JsonProcessingException {
        ObjectMapper maper=new ObjectMapper();
        maper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true);
        maper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);

       ZuphrLoada=maper.readValue(d, new TypeReference<List<LoadAction>>() {}).get(0);
        android.util.Log.i("response-plan-item" ,ZuphrLoada.FpName);

    }



    @JsonProperty("ZuphrMovem")
    String ZuphrMovem;

    @JsonProperty("ZuphrActtype")
    String ZuphrActtype;

    @JsonProperty("ZuphrContents")
    String ZuphrContents;


    @JsonProperty("ZuphrHeight")
    String ZuphrHeight;


    @JsonProperty("ZuphrLength")
    String ZuphrLength;


    @JsonProperty("ZuphrLpid")
    String ZuphrLpid;


    @JsonProperty("ZuphrObjecte")
    String ZuphrObjecte;

    @JsonProperty("ZuphrSchar")
    String ZuphrSchar;

    @JsonProperty("ZuphrFrom")
    String ZuphrFrom;

    @JsonProperty("ZuphrSchtask")
    String ZuphrSchtask;

    @JsonProperty("ZuphrSeq")
    String ZuphrSeq;

    @JsonProperty("ZuphrWidth")
    String ZuphrWidth;


    @JsonProperty("ZuphrShipper")
    String ZuphrShipper;

    @JsonProperty("ZuphrTo")
    String ZuphrTo;

    @JsonProperty("ZuphrVolmeins")
    String ZuphrVolmeins;

    @JsonProperty("ZuphrMjahr")
    String ZuphrMjahr;

    @JsonProperty("ZuphrMblpo")
    String ZuphrMblpo;

    @JsonProperty("ZuphrStgid")
    String ZuphrStgid;

    @JsonProperty("ZuphrMatnr")
    String ZuphrMatnr;

    @JsonProperty("ZuphrReqid")
    String ZuphrReqid;

    @JsonProperty("ZuphrReqitm")
    String ZuphrReqitm;

    @JsonProperty("ZuphrShortxt")
    String ZuphrShortxt;
    @JsonProperty("ZuphrDescrip")
    String ZuphrDescrip;

    @JsonProperty("ZuphrQuan")
    String ZuphrQuan;

    @JsonProperty("Meins")
    String Meins;

    @JsonProperty("ZuphrOffshore")
    String ZuphrOffshore;


    @JsonProperty("ZuphrAreacode")
    String ZuphrAreacode;

    @JsonProperty("ZuphrStatus")
    String ZuphrStatus;
    @JsonProperty("ZuphrClass")
    String ZuphrClass;

    @JsonProperty("ZuphrDeleted")
    String ZuphrDeleted;

    @JsonProperty("ZuphrStatdt")
    String ZuphrStatdt;

    @JsonProperty("ZuphrCompflg")
    String ZuphrCompflg;

    @JsonProperty("ZuphrCompdat")
    String ZuphrCompdat;
    @JsonProperty("ZuphrAction")
    String ZuphrAction;

    @JsonProperty("ZuphrResponse")
    String ZuphrResponse;

    @JsonProperty("ZuphrAutoact")
    String ZuphrAutoact;

    @JsonProperty("ZuphrActposgrp")
    String ZuphrActposgrp;

    @JsonProperty("ZuphrSent")
    String ZuphrSent;
    @JsonProperty("ZuphrActuname")
    String ZuphrActuname;

    @JsonProperty("ZuphrActdate")
    String ZuphrActdate;

    @JsonProperty("ZuphrActtime")
    String ZuphrActtime;

    @JsonProperty("ZuphrWellnm")
    String ZuphrWellnm;

    @JsonProperty("ZuphrEquipnumber")
    String ZuphrEquipnumber;
    @JsonProperty("ZuphrCnumber")
    String ZuphrCnumber;


    @JsonProperty("ZuphrTicketno")
    String ZuphrTicketno;

    @JsonProperty("ZuphrMfrpn")
    String ZuphrMfrpn;

    @JsonProperty("ZuphrBitType")
    String ZuphrBitType;

    @JsonProperty("ZuphrBitstatus")
    String ZuphrBitstatus;
    @JsonProperty("ZuphrSrlno")
    String ZuphrSrlno;

    @JsonProperty("ZuphrGl")
    String ZuphrGl;

    @JsonProperty("ZuphrCostcen")
    String ZuphrCostcen;

    @JsonProperty("ZuphrMattype")
    String ZuphrMattype;

    @JsonProperty("ZuphrFpDate")
    String ZuphrFpDate;

    @JsonProperty("ZuphrFpTime")
    String ZuphrFpTime;

    @JsonProperty("ZuphrFpName")
    String ZuphrFpName;



//    @JsonProperty("LoadingPlanLoadActionSet")
//    String LoadingPlanLoadActionSet;
//
//    @JsonProperty("LoadingPlanLoadMovemSet")
//    String LoadingPlanLoadMovemSet;
//
//    @JsonProperty("LoadingPlanLoadCharSet")
//    String LoadingPlanLoadCharSet;
//
//    @JsonProperty("LoadingPlanLoadCheckSet")
//    String LoadingPlanLoadCheckSet;


    public Material2(String zuphrActquan, String log, LoadAction zuphrLoada, String zuphrMovem, String zuphrActtype,
                     String zuphrContents, String zuphrHeight, String zuphrLength, String zuphrLpid,
                     String zuphrObjecte, String zuphrSchar, String zuphrFrom, String zuphrSchtask,
                     String zuphrSeq, String zuphrWidth, String zuphrShipper, String zuphrTo,
                     String zuphrVolmeins, String zuphrMjahr, String zuphrMblpo, String zuphrStgid,
                     String zuphrMatnr, String zuphrReqid, String zuphrReqitm, String zuphrShortxt,
                     String zuphrDescrip, String zuphrQuan, String meins, String zuphrOffshore,
                     String zuphrAreacode, String zuphrStatus, String zuphrClass, String zuphrDeleted,
                     String zuphrStatdt, String zuphrCompflg, String zuphrCompdat, String zuphrAction,
                     String zuphrResponse, String zuphrAutoact, String zuphrActposgrp, String zuphrSent,
                     String zuphrActuname, String zuphrActdate, String zuphrActtime, String zuphrWellnm,
                     String zuphrEquipnumber, String zuphrCnumber, String zuphrTicketno, String zuphrMfrpn,
                     String zuphrBitType, String zuphrBitstatus, String zuphrSrlno, String zuphrGl,
                     String zuphrCostcen, String zuphrMattype, String zuphrFpDate, String zuphrFpTime,
                     String zuphrFpName ) {
        ZuphrActquan = zuphrActquan;
        Log = log;
        ZuphrLoada = zuphrLoada;
        ZuphrMovem = zuphrMovem;
        ZuphrActtype = zuphrActtype;
        ZuphrContents = zuphrContents;
        ZuphrHeight = zuphrHeight;
        ZuphrLength = zuphrLength;
        ZuphrLpid = zuphrLpid;
        ZuphrObjecte = zuphrObjecte;
        ZuphrSchar = zuphrSchar;
        ZuphrFrom = zuphrFrom;
        ZuphrSchtask = zuphrSchtask;
        ZuphrSeq = zuphrSeq;
        ZuphrWidth = zuphrWidth;
        ZuphrShipper = zuphrShipper;
        ZuphrTo = zuphrTo;
        ZuphrVolmeins = zuphrVolmeins;
        ZuphrMjahr = zuphrMjahr;
        ZuphrMblpo = zuphrMblpo;
        ZuphrStgid = zuphrStgid;
        ZuphrMatnr = zuphrMatnr;
        ZuphrReqid = zuphrReqid;
        ZuphrReqitm = zuphrReqitm;
        ZuphrShortxt = zuphrShortxt;
        ZuphrDescrip = zuphrDescrip;
        ZuphrQuan = zuphrQuan;
        Meins = meins;
        ZuphrOffshore = zuphrOffshore;
        ZuphrAreacode = zuphrAreacode;
        ZuphrStatus = zuphrStatus;
        ZuphrClass = zuphrClass;
        ZuphrDeleted = zuphrDeleted;
        ZuphrStatdt = zuphrStatdt;
        ZuphrCompflg = zuphrCompflg;
        ZuphrCompdat = zuphrCompdat;
        ZuphrAction = zuphrAction;
        ZuphrResponse = zuphrResponse;
        ZuphrAutoact = zuphrAutoact;
        ZuphrActposgrp = zuphrActposgrp;
        ZuphrSent = zuphrSent;
        ZuphrActuname = zuphrActuname;
        ZuphrActdate = zuphrActdate;
        ZuphrActtime = zuphrActtime;
        ZuphrWellnm = zuphrWellnm;
        ZuphrEquipnumber = zuphrEquipnumber;
        ZuphrCnumber = zuphrCnumber;
        ZuphrTicketno = zuphrTicketno;
        ZuphrMfrpn = zuphrMfrpn;
        ZuphrBitType = zuphrBitType;
        ZuphrBitstatus = zuphrBitstatus;
        ZuphrSrlno = zuphrSrlno;
        ZuphrGl = zuphrGl;
        ZuphrCostcen = zuphrCostcen;
        ZuphrMattype = zuphrMattype;
        ZuphrFpDate = zuphrFpDate;
        ZuphrFpTime = zuphrFpTime;
        ZuphrFpName = zuphrFpName;


    }

    public Material2() {

    }

    public String getZuphrActquan() {
        return ZuphrActquan;
    }

    public void setZuphrActquan(String zuphrActquan) {
        ZuphrActquan = zuphrActquan;
    }

    public String getLog() {
        return Log;
    }

    public void setLog(String log) {
        Log = log;
    }

    public LoadAction getZuphrLoada() {
        return ZuphrLoada;
    }

    public void setZuphrLoada(LoadAction zuphrLoada) {
        ZuphrLoada = zuphrLoada;
    }

    public String getZuphrMovem() {
        return ZuphrMovem;
    }

    public void setZuphrMovem(String zuphrMovem) {
        ZuphrMovem = zuphrMovem;
    }

    public String getZuphrActtype() {
        return ZuphrActtype;
    }

    public void setZuphrActtype(String zuphrActtype) {
        ZuphrActtype = zuphrActtype;
    }

    public String getZuphrContents() {
        return ZuphrContents;
    }

    public void setZuphrContents(String zuphrContents) {
        ZuphrContents = zuphrContents;
    }

    public String getZuphrHeight() {
        return ZuphrHeight;
    }

    public void setZuphrHeight(String zuphrHeight) {
        ZuphrHeight = zuphrHeight;
    }

    public String getZuphrLength() {
        return ZuphrLength;
    }

    public void setZuphrLength(String zuphrLength) {
        ZuphrLength = zuphrLength;
    }

    public String getZuphrLpid() {
        return ZuphrLpid;
    }

    public void setZuphrLpid(String zuphrLpid) {
        ZuphrLpid = zuphrLpid;
    }

    public String getZuphrObjecte() {
        return ZuphrObjecte;
    }

    public void setZuphrObjecte(String zuphrObjecte) {
        ZuphrObjecte = zuphrObjecte;
    }

    public String getZuphrSchar() {
        return ZuphrSchar;
    }

    public void setZuphrSchar(String zuphrSchar) {
        ZuphrSchar = zuphrSchar;
    }

    public String getZuphrFrom() {
        return ZuphrFrom;
    }

    public void setZuphrFrom(String zuphrFrom) {
        ZuphrFrom = zuphrFrom;
    }

    public String getZuphrSchtask() {
        return ZuphrSchtask;
    }

    public void setZuphrSchtask(String zuphrSchtask) {
        ZuphrSchtask = zuphrSchtask;
    }

    public String getZuphrSeq() {
        return ZuphrSeq;
    }

    public void setZuphrSeq(String zuphrSeq) {
        ZuphrSeq = zuphrSeq;
    }

    public String getZuphrWidth() {
        return ZuphrWidth;
    }

    public void setZuphrWidth(String zuphrWidth) {
        ZuphrWidth = zuphrWidth;
    }

    public String getZuphrShipper() {
        return ZuphrShipper;
    }

    public void setZuphrShipper(String zuphrShipper) {
        ZuphrShipper = zuphrShipper;
    }

    public String getZuphrTo() {
        return ZuphrTo;
    }

    public void setZuphrTo(String zuphrTo) {
        ZuphrTo = zuphrTo;
    }

    public String getZuphrVolmeins() {
        return ZuphrVolmeins;
    }

    public void setZuphrVolmeins(String zuphrVolmeins) {
        ZuphrVolmeins = zuphrVolmeins;
    }

    public String getZuphrMjahr() {
        return ZuphrMjahr;
    }

    public void setZuphrMjahr(String zuphrMjahr) {
        ZuphrMjahr = zuphrMjahr;
    }

    public String getZuphrMblpo() {
        return ZuphrMblpo;
    }

    public void setZuphrMblpo(String zuphrMblpo) {
        ZuphrMblpo = zuphrMblpo;
    }

    public String getZuphrStgid() {
        return ZuphrStgid;
    }

    public void setZuphrStgid(String zuphrStgid) {
        ZuphrStgid = zuphrStgid;
    }

    public String getZuphrMatnr() {
        return ZuphrMatnr;
    }

    public void setZuphrMatnr(String zuphrMatnr) {
        ZuphrMatnr = zuphrMatnr;
    }

    public String getZuphrReqid() {
        return ZuphrReqid;
    }

    public void setZuphrReqid(String zuphrReqid) {
        ZuphrReqid = zuphrReqid;
    }

    public String getZuphrReqitm() {
        return ZuphrReqitm;
    }

    public void setZuphrReqitm(String zuphrReqitm) {
        ZuphrReqitm = zuphrReqitm;
    }

    public String getZuphrShortxt() {
        return ZuphrShortxt;
    }

    public void setZuphrShortxt(String zuphrShortxt) {
        ZuphrShortxt = zuphrShortxt;
    }

    public String getZuphrDescrip() {
        return ZuphrDescrip;
    }

    public void setZuphrDescrip(String zuphrDescrip) {
        ZuphrDescrip = zuphrDescrip;
    }

    public String getZuphrQuan() {
        return ZuphrQuan;
    }

    public void setZuphrQuan(String zuphrQuan) {
        ZuphrQuan = zuphrQuan;
    }

    public String getMeins() {
        return Meins;
    }

    public void setMeins(String meins) {
        Meins = meins;
    }

    public String getZuphrOffshore() {
        return ZuphrOffshore;
    }

    public void setZuphrOffshore(String zuphrOffshore) {
        ZuphrOffshore = zuphrOffshore;
    }

    public String getZuphrStatus() {
        return ZuphrStatus;
    }

    public void setZuphrStatus(String zuphrStatus) {
        ZuphrStatus = zuphrStatus;
    }

    public String getZuphrClass() {
        return ZuphrClass;
    }

    public void setZuphrClass(String zuphrClass) {
        ZuphrClass = zuphrClass;
    }

    public String getZuphrDeleted() {
        return ZuphrDeleted;
    }

    public void setZuphrDeleted(String zuphrDeleted) {
        ZuphrDeleted = zuphrDeleted;
    }

    public String getZuphrStatdt() {
        return ZuphrStatdt;
    }

    public void setZuphrStatdt(String zuphrStatdt) {
        ZuphrStatdt = zuphrStatdt;
    }

    public String getZuphrCompflg() {
        return ZuphrCompflg;
    }

    public void setZuphrCompflg(String zuphrCompflg) {
        ZuphrCompflg = zuphrCompflg;
    }

    public String getZuphrAreacode() {
        return ZuphrAreacode;
    }

    public void setZuphrAreacode(String zuphrAreacode) {
        ZuphrAreacode = zuphrAreacode;
    }

    public String getZuphrCompdat() {
        return ZuphrCompdat;
    }

    public void setZuphrCompdat(String zuphrCompdat) {
        ZuphrCompdat = zuphrCompdat;
    }

    public String getZuphrAction() {
        return ZuphrAction;
    }

    public void setZuphrAction(String zuphrAction) {
        ZuphrAction = zuphrAction;
    }

    public String getZuphrResponse() {
        return ZuphrResponse;
    }

    public void setZuphrResponse(String zuphrResponse) {
        ZuphrResponse = zuphrResponse;
    }

    public String getZuphrAutoact() {
        return ZuphrAutoact;
    }

    public void setZuphrAutoact(String zuphrAutoact) {
        ZuphrAutoact = zuphrAutoact;
    }

    public String getZuphrActposgrp() {
        return ZuphrActposgrp;
    }

    public void setZuphrActposgrp(String zuphrActposgrp) {
        ZuphrActposgrp = zuphrActposgrp;
    }

    public String getZuphrSent() {
        return ZuphrSent;
    }

    public void setZuphrSent(String zuphrSent) {
        ZuphrSent = zuphrSent;
    }

    public String getZuphrActuname() {
        return ZuphrActuname;
    }

    public void setZuphrActuname(String zuphrActuname) {
        ZuphrActuname = zuphrActuname;
    }

    public String getZuphrActdate() {
        return ZuphrActdate;
    }

    public void setZuphrActdate(String zuphrActdate) {
        ZuphrActdate = zuphrActdate;
    }

    public String getZuphrActtime() {
        return ZuphrActtime;
    }

    public void setZuphrActtime(String zuphrActtime) {
        ZuphrActtime = zuphrActtime;
    }

    public String getZuphrWellnm() {
        return ZuphrWellnm;
    }

    public void setZuphrWellnm(String zuphrWellnm) {
        ZuphrWellnm = zuphrWellnm;
    }

    public String getZuphrEquipnumber() {
        return ZuphrEquipnumber;
    }

    public void setZuphrEquipnumber(String zuphrEquipnumber) {
        ZuphrEquipnumber = zuphrEquipnumber;
    }

    public String getZuphrCnumber() {
        return ZuphrCnumber;
    }

    public void setZuphrCnumber(String zuphrCnumber) {
        ZuphrCnumber = zuphrCnumber;
    }

    public String getZuphrTicketno() {
        return ZuphrTicketno;
    }

    public void setZuphrTicketno(String zuphrTicketno) {
        ZuphrTicketno = zuphrTicketno;
    }

    public String getZuphrMfrpn() {
        return ZuphrMfrpn;
    }

    public void setZuphrMfrpn(String zuphrMfrpn) {
        ZuphrMfrpn = zuphrMfrpn;
    }

    public String getZuphrBitType() {
        return ZuphrBitType;
    }

    public void setZuphrBitType(String zuphrBitType) {
        ZuphrBitType = zuphrBitType;
    }

    public String getZuphrBitstatus() {
        return ZuphrBitstatus;
    }

    public void setZuphrBitstatus(String zuphrBitstatus) {
        ZuphrBitstatus = zuphrBitstatus;
    }

    public String getZuphrSrlno() {
        return ZuphrSrlno;
    }

    public void setZuphrSrlno(String zuphrSrlno) {
        ZuphrSrlno = zuphrSrlno;
    }

    public String getZuphrGl() {
        return ZuphrGl;
    }

    public void setZuphrGl(String zuphrGl) {
        ZuphrGl = zuphrGl;
    }

    public String getZuphrCostcen() {
        return ZuphrCostcen;
    }

    public void setZuphrCostcen(String zuphrCostcen) {
        ZuphrCostcen = zuphrCostcen;
    }

    public String getZuphrMattype() {
        return ZuphrMattype;
    }

    public void setZuphrMattype(String zuphrMattype) {
        ZuphrMattype = zuphrMattype;
    }

    public String getZuphrFpDate() {
        return ZuphrFpDate;
    }

    public void setZuphrFpDate(String zuphrFpDate) {
        ZuphrFpDate = zuphrFpDate;
    }

    public String getZuphrFpTime() {
        return ZuphrFpTime;
    }

    public void setZuphrFpTime(String zuphrFpTime) {
        ZuphrFpTime = zuphrFpTime;
    }

    public String getZuphrFpName() {
        return ZuphrFpName;
    }

    public void setZuphrFpName(String zuphrFpName) {
        ZuphrFpName = zuphrFpName;
    }





}

