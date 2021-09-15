package com.example.currencyconverter.main

import com.example.currencyconverter.Converter
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
                    repository.setModel(it)

                    val currencies = repository.getListOfCurrencies()

                    view?.bindCurrencies(currencies)
                    view?.bindUpdateTime(repository.getTime())
                    view?.bindCharCodes(repository.getCharCodes())
                }
            )
        compositeDisposable.add(disposable)
    }

    //произошло нажатие на элемент списка
    fun onCurrencyClicked(currency: Currency){
        view?.setSpinnerSelection(repository.getPosition(currency))
    }

    //обновились данные в edit text
    fun editTextUpdate(rubbles: String, pos: Int){
        val rub = rubbles.toDoubleOrNull()

        if (rub != null){
            val otherCurrency = repository.getCurrency(pos)
            val valueOtherCurrency = Converter.convertRubToOther(rub, otherCurrency.value, otherCurrency.nominal)
            view?.updateConverter(valueOtherCurrency)
        }
        else view?.clearOtherCurrency()
    }
}