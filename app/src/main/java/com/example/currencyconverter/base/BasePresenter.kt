package com.example.currencyconverter.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter <View : BaseView> {
    protected var view: View? = null
    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun attachView(view: View){
        this.view = view
    }

    fun destroy(){
        this.view = null
    }

    fun onCleared(){
        compositeDisposable.clear()
    }
}