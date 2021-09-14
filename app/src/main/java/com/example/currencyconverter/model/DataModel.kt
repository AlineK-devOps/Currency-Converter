package com.example.currencyconverter.model

import com.google.gson.annotations.SerializedName

data class DataModel(

    @SerializedName("Date")
    val date: String,

    @SerializedName("PreviousDate")
    val previousDate: String,

    @SerializedName("PreviousURL")
    val previousUrl: String,

    @SerializedName("Timestamp")
    val timeStamp: String,

    @SerializedName("Valute")
    val valute: Valute
)