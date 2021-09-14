package com.example.currencyconverter.data

import com.example.currencyconverter.model.DataModel
import io.reactivex.Single


interface CurrencyRepository {
    fun getValute(): Single<DataModel>
    fun setModel(model: DataModel)
    fun getModel(): DataModel
}