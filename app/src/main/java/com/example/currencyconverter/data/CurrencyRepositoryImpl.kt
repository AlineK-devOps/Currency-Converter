package com.example.currencyconverter.data

import com.example.currencyconverter.CurrenciesDatasource
import com.example.currencyconverter.CurrenciesDatasourceImpl
import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.model.DataModel
import com.google.gson.GsonBuilder
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CurrencyRepositoryImpl private constructor(
    private val currenciesDatasource: CurrenciesDatasource
): CurrencyRepository {

    companion object{
        var BASE_URL = "https://www.cbr-xml-daily.ru/"

        private var repository: CurrencyRepositoryImpl? = null

        private var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()

        fun getInstance(): CurrencyRepositoryImpl {
            if (repository == null)
                repository = CurrencyRepositoryImpl(CurrenciesDatasourceImpl(retrofit.create()))

            return repository as CurrencyRepositoryImpl
        }
    }

    override fun setModel(model: DataModel){
        currenciesDatasource.setModel(model)
    }

    override fun getModel(): DataModel =
        currenciesDatasource.getModel()

    override fun getListOfCurrencies(): List<Currency> =
        currenciesDatasource.getListOfCurrencies()

    override fun getCharCodes(): List<String> =
        currenciesDatasource.getCharCodes()

    override fun getTime(): String =
        currenciesDatasource.getTime()

    override fun getPosition(currency: Currency): Int =
        currenciesDatasource.getPosition(currency)

    override fun getCurrency(pos: Int): Currency =
        currenciesDatasource.getCurrency(pos)

    override fun getValute(): Single<DataModel> =
        currenciesDatasource.getValute()
}