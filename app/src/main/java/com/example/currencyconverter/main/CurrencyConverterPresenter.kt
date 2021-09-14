package com.example.currencyconverter.main

import com.example.currencyconverter.base.BasePresenter
import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.data.CurrencyRepository
import com.example.currencyconverter.model.Valute
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import java.lang.ClassCastException

class CurrencyConverterPresenter(private val repository: CurrencyRepository) : BasePresenter<CurrencyConverterView>() {

    //заполняем активити данными
    fun onScreenResumed(){
        val disposable = repository.getValute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    val currencies = getListOfCurrencies(it.valute)

                    view?.bindCurrencies(currencies)
                    view?.bindUpdateTime(getTime(it.timeStamp))
                    view?.bindCharCodes(getCharCodes(currencies))

                    repository.setModel(it)
                }
            )
        compositeDisposable.add(disposable)
    }

    //произошло нажатие на элемент списка
    fun onCurrencyClicked(currency: Currency){
        val charCodes = getCharCodes(getListOfCurrencies(repository.getModel().valute))

        var pos: Int = charCodes.indexOf(currency.charCode)

        view?.setSpinnerSelection(pos)
    }

    //пересчитываем значения валюты
    fun calculate(rubbles: String, pos: Int){
        val rub = rubbles.toDoubleOrNull()

        if (rub != null){
            val otherCurrency = getListOfCurrencies(repository.getModel().valute)[pos]
            val valueOtherCurrency = calculate(rub, otherCurrency.value, otherCurrency.nominal)
            view?.updateConverter(valueOtherCurrency)
        }
    }

    private fun calculate(rubbles: Double, value: Double, nominal: Int): Double =
        rubbles * value / nominal

    //получаем список кодов валют
    private fun getCharCodes(currencies: List<Currency>): List<String>{
        return currencies.map { it.charCode }
    }

    //получаем время загрузки
    private fun getTime(timeStamp: String): String{
        return "${timeStamp.substringBefore('T')} ${timeStamp.substringAfter('T').substringBefore('+')}"
    }

    //получаем список валюты
    private fun getListOfCurrencies(valute: Valute): List<Currency>{
        var currencies = mutableListOf<Currency>()

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