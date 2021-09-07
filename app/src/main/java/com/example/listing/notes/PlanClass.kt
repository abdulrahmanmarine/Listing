package com.example.listing
import androidx.core.content.res.FontResourcesParserCompat
import com.fasterxml.jackson.annotation.*

class dVariableJSON<T> {
    @JsonProperty("d")
    var d: T? = null
}


@JsonIgnoreProperties(ignoreUnknown = true)
class resultVariableJSON<T> {
    @JsonProperty("results")
    var results: T? = null

}

data class Notes(
    val creator: String = "",
    var noteText: String = "",
    val type: String = "",
    val time: String = "",
    val date: String = ""
)


@JsonIgnoreProperties(ignoreUnknown = true)
data class SAPNote(

    @JsonProperty("ZuphrMjahr")
    var ZuphrMjahr:String? = "",
    @JsonProperty("ZuphrType")
    var ZuphrType: String = "",
    @JsonProperty("ZuphrNoteId")
    var ZuphrNoteId: String = "",
    @JsonProperty("ZuphrContent")
    var ZuphrContent: String = "",
    @JsonProperty("ZuphrId1")
    var ZuphrId1: String = "",
    @JsonProperty("ZuphrId2")
    var ZuphrId2: String? = "",
    @JsonProperty("ZuphrId3")
    var ZuphrId3: String? = "",
    @JsonProperty("ZuphrContentType")
    var ZuphrContentType: String = "",
    @JsonProperty("Lat")
    var Lat: String? = "",
    @JsonProperty("Lon")
    var Lon: String? = "",
    @JsonProperty("ZuphrFpName")
    var ZuphrFpName: String? = "",
    @JsonProperty("ZuphrFpTime")
    var ZuphrFpTime: String? = "",
    @JsonProperty("ZuphrFpDate")
    var ZuphrFpDate: String? = "",

    )

