package com.example.currencyconverter.data

import com.example.currencyconverter.model.DataModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi{
    @GET("daily_json.js")
    fun getValute(): Single<Response<DataModel>>
}