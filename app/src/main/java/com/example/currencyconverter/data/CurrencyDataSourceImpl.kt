package com.example.currencyconverter

import com.example.currencyconverter.data.CurrencyApi
import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.model.DataModel
import com.example.currencyconverter.model.Valute
import io.reactivex.Single

interface CurrenciesDatasource{
    fun getValute(): Single<DataModel>
    fun setModel(model: DataModel)
    fun getModel(): DataModel
}

class CurrenciesDatasourceImpl(private val currencyApi: CurrencyApi) : CurrenciesDatasource {
    private var currency: DataModel = DataModel(
        "none",
        "none",
        "none",
        "none",
        Valute(
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0),
            Currency("0", "0", "0", 0, "none", 0.0, 0.0)
        )
    )

    //выкачиваем данные
    override fun getValute(): Single<DataModel> =
        currencyApi.getValute()
            .map {
                it.body() ?: currency
            }

    override fun setModel(model: DataModel){
        currency = model
    }

    override fun getModel(): DataModel = currency
}