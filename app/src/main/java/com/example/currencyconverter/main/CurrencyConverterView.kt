package com.example.currencyconverter.main

import com.example.currencyconverter.base.BaseView
import com.example.currencyconverter.model.Currency

interface CurrencyConverterView : BaseView {
    fun bindCharCodes(charCodes: List<String>)
    fun bindCurrencies(currencies: List<Currency>)
    fun clearCurrencies()
    fun bindUpdateTime(time: String)
    fun updateConverter(otherCurrency: Double)
    fun setSpinnerSelection(pos: Int)
    fun clearOtherCurrency()
}