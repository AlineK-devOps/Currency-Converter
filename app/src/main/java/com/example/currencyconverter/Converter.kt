package com.example.currencyconverter

object Converter {
    //рубли в другую валюту
    fun convertRubToOther(rubbles: Double, rate: Double, nominal: Int) =
        rubbles * rate / nominal
}