package com.example.currencyconverter.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.data.CurrencyRepositoryImpl

import android.view.View
import android.widget.*


class CurrencyConverterActivity : AppCompatActivity(), CurrencyConverterView {

    private val presenter by lazy {
        CurrencyConverterPresenter(CurrencyRepositoryImpl.getInstance())
    }

    private val adapter by lazy { CurrencyAdapter(presenter::onCurrencyClicked) }

    private lateinit var currencyList: RecyclerView
    private lateinit var lastUpdateText: TextView
    private lateinit var rubbles: EditText
    private lateinit var otherCurrency: EditText
    private lateinit var spinner: Spinner
    private lateinit var updateButton: ImageButton

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_converter)

        prefs = getSharedPreferences(CurrencyRepositoryImpl.APP_PREFERENCES, MODE_PRIVATE)

        presenter.attachView(this)

        rubbles = findViewById(R.id.rubblesEt)
        rubbles.addTextChangedListener { presenter.editTextUpdate(
            rubbles.text.toString(),
            spinner.selectedItemPosition
        ) }


        spinner = findViewById(R.id.otherCurrencySpinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                clearOtherCurrency()
                presenter.editTextUpdate(rubbles.text.toString(), position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        updateButton = findViewById(R.id.updateButton)
        updateButton.setOnClickListener {
            presenter.onUpdateButtonClicked()
        }

        otherCurrency = findViewById(R.id.otherCurrencyEt)
        lastUpdateText = findViewById(R.id.lastUpdateText)

        currencyList = findViewById(R.id.currenciesList)
        currencyList.adapter = adapter
        currencyList.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.onScreenStarted(prefs)
    }

    override fun onPause() {
        super.onPause()
        presenter.onScreenPaused(prefs)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("rubbles", rubbles.text.toString())
        outState.putInt("position", spinner.selectedItemPosition)
        rubbles.setText("")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState.containsKey("rubbles") && savedInstanceState.containsKey("position")){
            val r = savedInstanceState.getString("rubbles")
            rubbles.setText(r)
            val i = savedInstanceState.getInt("position")
            setSpinnerSelection(i)
        }
    }

    //заполняем спиннер
    override fun bindCharCodes(charCodes: List<String>) {
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            charCodes
        )
        spinner.adapter = adapter
    }

    //заполняем список
    override fun bindCurrencies(currencies: List<Currency>){
        adapter.currencies = currencies
    }

    override fun clearCurrencies() {
        adapter.currencies = emptyList()
    }

    //заполняем дату и время загрузки
    override fun bindUpdateTime(time: String) {
        lastUpdateText.text = getString(R.string.last_updated, time)
    }

    //обновляем поля конвертера
    override fun updateConverter(otherCurrency: Double) {
        this.otherCurrency.setText(String.format("%.4f", otherCurrency))
    }

    //устанавливаем выбранное значение в спиннер
    override fun setSpinnerSelection(pos: Int) {
        spinner.setSelection(pos)
    }

    //очищаем поле с второй валютой
    override fun clearOtherCurrency() {
        otherCurrency.setText("")
    }
}