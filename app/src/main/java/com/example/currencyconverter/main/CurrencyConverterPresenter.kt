package com.example.currencyconverter.main

import android.content.SharedPreferences
import com.example.currencyconverter.Converter
import com.example.currencyconverter.base.BasePresenter
import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.data.CurrencyRepository
import com.example.currencyconverter.data.CurrencyRepositoryImpl
import com.example.currencyconverter.model.DataModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

class CurrencyConverterPresenter(private val repository: CurrencyRepository) : BasePresenter<CurrencyConverterView>() {

    //активность стартует
    fun onScreenStarted(prefs: SharedPreferences){
        if (!prefs.contains(CurrencyRepositoryImpl.APP_PREFERENCES)){
            loadDataFromNet()
        }
        else{
            loadDataFromPrefs(prefs)
            bindActivity()
        }
    }

    //окно прерывается
    fun onScreenPaused(prefs: SharedPreferences){
        if (!prefs.contains(CurrencyRepositoryImpl.APP_PREFERENCES)){
            saveDataToPrefs(prefs)
        }
    }

    //нажата кнопка обновления курсов валют
    fun onUpdateButtonClicked(){
        loadDataFromNet()
    }

    //произошло нажатие на элемент списка
    fun onCurrencyClicked(currency: Currency){
        view?.setSpinnerSelection(repository.getPosition(currency))
    }

    //обновились данные в edit text
    fun editTextUpdate(rubbles: String, pos: Int){
        val rub = rubbles.toDoubleOrNull()

        if (rub != null && pos >= 0){
            val otherCurrency = repository.getCurrency(pos)
            val valueOtherCurrency = Converter.convertRubToOther(rub, otherCurrency.value, otherCurrency.nominal)
            view?.updateConverter(valueOtherCurrency)
        }
        else view?.clearOtherCurrency()
     }

    //устанавливаем views значения
    private fun bindActivity(){
        val currencies = repository.getListOfCurrencies()

        view?.bindCurrencies(currencies)
        view?.bindUpdateTime(repository.getTime())
        view?.bindCharCodes(repository.getCharCodes())
    }

    //загружаем объект из сети
    private fun loadDataFromNet(){
        val disposable = repository.getValute()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    repository.setModel(it)
                    bindActivity()
                }
            )
        compositeDisposable.add(disposable)
    }


    //загружаем объект из prefs
    private fun loadDataFromPrefs(prefs: SharedPreferences){
        val json = prefs.getString(CurrencyRepositoryImpl.APP_PREFERENCES, "")
        repository.setModel(Gson().fromJson(json, DataModel::class.java))
    }

    //сохраняем объект в prefs
    private fun saveDataToPrefs(prefs: SharedPreferences){
        val editor = prefs.edit()
        val json = Gson().toJson(repository.getModel())
        editor.putString(CurrencyRepositoryImpl.APP_PREFERENCES, json)
        editor.apply()
    }
}