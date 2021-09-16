package com.example.currencyconverter.utils

object Converter {
    //перевод рублей в другую валюту
    fun convertRubToOther(rubbles: Double, rate: Double, nominal: Int) =
        rubbles * rate / nominal
}