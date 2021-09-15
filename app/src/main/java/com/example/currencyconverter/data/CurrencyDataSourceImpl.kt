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
    fun getListOfCurrencies(): List<Currency>
    fun getCharCodes(): List<String>
    fun getTime(): String
    fun getPosition(currency: Currency): Int
    fun getCurrency(pos: Int): Currency
}

class CurrenciesDatasourceImpl(private val currencyApi: CurrencyApi) : CurrenciesDatasource {
    private var model: DataModel = DataModel(
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
                it.body() ?: model
            }

    //устанавливаем модель
    override fun setModel(model: DataModel){
        this.model = model
    }

    //получаем модель
    override fun getModel(): DataModel = model

    //получаем список кодов валют
    override fun getCharCodes(): List<String>{
        return getListOfCurrencies().map { it.charCode }
    }

    //получаем время загрузки
    override fun getTime(): String{
        val timeStamp = model.timeStamp
        return "${timeStamp.substringBefore('T')} ${timeStamp.substringAfter('T').substringBefore('+')}"
    }

    //получаем позицию в списке по валюте
    override fun getPosition(currency: Currency): Int =
        getCharCodes().indexOf(currency.charCode)

    //получаем валюту по позиции в списке
    override fun getCurrency(pos: Int): Currency =
        getListOfCurrencies()[pos]

    //получаем список валюты
    override fun getListOfCurrencies(): List<Currency>{
        val currencies = mutableListOf<Currency>()
        val valute = model.valute

        currencies.add(valute.AMD)
        currencies.add(valute.AUD)
        currencies.add(valute.AZN)
        currencies.add(valute.GBP)
        currencies.add(valute.BYN)
        currencies.add(valute.BGN)
        currencies.add(valute.BRL)
        currencies.add(valute.HUF)
        currencies.add(valute.HKD)
        currencies.add(valute.DKK)

        currencies.add(valute.USD)
        currencies.add(valute.EUR)
        currencies.add(valute.INR)
        currencies.add(valute.KZT)
        currencies.add(valute.CAD)
        currencies.add(valute.KGS)
        currencies.add(valute.CNY)
        currencies.add(valute.MDL)
        currencies.add(valute.NOK)
        currencies.add(valute.PLN)

        currencies.add(valute.RON)
        currencies.add(valute.XDR)
        currencies.add(valute.SGD)
        currencies.add(valute.TJS)
        currencies.add(valute.TRY)
        currencies.add(valute.TMT)
        currencies.add(valute.UZS)
        currencies.add(valute.UAH)
        currencies.add(valute.CZK)
        currencies.add(valute.SEK)

        currencies.add(valute.CHF)
        currencies.add(valute.ZAR)
        currencies.add(valute.KRW)
        currencies.add(valute.JPY)

        return currencies
    }
}