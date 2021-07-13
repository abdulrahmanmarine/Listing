package com.example.listing.Kotlin

data class Notes(
    val creator: String = "",
    var noteText:String = "",
    val type:Int = 0
//0: text
//1: voice
//2: image
)