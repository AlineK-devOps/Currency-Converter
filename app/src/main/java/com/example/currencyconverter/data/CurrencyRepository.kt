package com.example.currencyconverter.data

import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.model.DataModel
import io.reactivex.Single


interface CurrencyRepository {
    fun getValute(): Single<DataModel>
    fun setModel(model: DataModel)
    fun getModel(): DataModel
    fun getListOfCurrencies(): List<Currency>
    fun getCharCodes(): List<String>
    fun getTime(): String
    fun getPosition(currency: Currency): Int
    fun getCurrency(pos: Int): Currency
}