package com.example.listing.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class LoadAction(
        @JsonProperty("ZuphrLpid") var ZuphrLpid: String? = "",
        @JsonProperty("ZuphrMjahr") var actionMjahr: String? = "",
        @JsonProperty("ZuphrMblpo") var mblpo: String? = "",
        @JsonProperty("ZuphrLoadid") var loadingId: String? = "",
        @JsonProperty("ZuphrActtype") var actionType : String? = "",
        @JsonIgnore var ZuphrReady: Boolean? = false,
        @JsonProperty("ZuphrFpDate") var fp_date: String? = "",
        @JsonProperty("ZuphrFpTime") var fp_time: String? = "",
        @JsonProperty("ZuphrFpName") var fp_name: String? = "",
        @JsonProperty("ZuphrAsinQuan") var assignedQuan: String? = 0.0.toString(),
        @JsonProperty("ZuphrMeins") var materialUnit: String? = "",
        @JsonProperty("ZuphrSize") var ZuphrSize: String? = "",
        @JsonProperty("ZuphrConfQuan") var confirmedQuan: String? = 0.0.toString(),
        @JsonProperty("ZuphrAct") var ZuphrAct: String? = "",
        @JsonProperty("ZuphrDriver") var driver: String? = "",
        @JsonProperty("ZuphrWeight") var weight: String? = "",
        @JsonProperty("ZuphrVehType") var vehicle: String? = "",
        @JsonProperty("ZuphrStatus") var status: String? = "",
)

{

    @JsonProperty("ZuphrReady")
    private fun setReady(flag: String) {
        ZuphrReady = (flag == "X")
    }


}
