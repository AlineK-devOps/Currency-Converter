package com.example.currencyconverter.main

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.currencyconverter.utils.Converter
import com.example.currencyconverter.base.BasePresenter
import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.data.CurrencyRepository
import com.example.currencyconverter.data.CurrencyRepositoryImpl
import com.example.currencyconverter.model.DataModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CurrencyConverterPresenter(private val repository: CurrencyRepository) : BasePresenter<CurrencyConverterView>() {

    //активность стартует
    fun onScreenStarted(prefs: SharedPreferences){
        //проверяем существует ли сохраненная копия курсов
        if (!prefs.contains(CurrencyRepositoryImpl.APP_PREFERENCES)){
            loadDataFromNet() //не существует - загружаем данные из сети
        }
        else{
            loadDataFromPrefs(prefs) //существует - достаём копию

            //проверяем нужно ли обновить копию
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && needToUpdate(repository.getTime()))
                loadDataFromNet()
            else
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
        view?.clearCurrencies()
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

    //устнавливаем данные
    private fun bindActivity(){
        view?.bindCurrencies(repository.getListOfCurrencies())
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

    //прошло ли больше суток с последнего обновления
    @RequiresApi(Build.VERSION_CODES.O)
    private fun needToUpdate(loadDate: String): Boolean{
        val parsedDate = LocalDateTime.parse(loadDate, DateTimeFormatter.ofPattern("y-M-d H:m:s"))

        if (parsedDate != null){
            val hours = parsedDate.until(LocalDateTime.now(), ChronoUnit.HOURS)

            if (hours >= 24)
                return true
        }
        return false
    }
}