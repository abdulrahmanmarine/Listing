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



