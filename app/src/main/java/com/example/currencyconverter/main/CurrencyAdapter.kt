package com.example.currencyconverter.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import com.example.currencyconverter.model.Currency

class CurrencyAdapter(private val onItemClick: (Currency) -> Unit) : RecyclerView.Adapter<CurrencyHolder>(){

    var currencies: List<Currency> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view = (LayoutInflater.from(parent.context)).inflate(R.layout.list_item_currency, parent, false)

        return CurrencyHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        val currency = currencies[position]
        holder.bind(currency)
    }

    override fun getItemCount(): Int = currencies.size
}

class CurrencyHolder(
    view: View,
    private val onItemClick: (Currency) -> Unit
) : RecyclerView.ViewHolder(view){

    private val charCode = view.findViewById<TextView>(R.id.charCodeText)
    private val nameAndNominal = view.findViewById<TextView>(R.id.nameAndNominalText)
    private val value = view.findViewById<TextView>(R.id.currencyValueText)

    fun bind(currency: Currency){
        charCode.text = itemView.context.getString(R.string.char_code_format, currency.charCode)
        nameAndNominal.text = itemView.context.getString(R.string.name_nominal_format, currency.nominal, currency.name)
        value.text = itemView.context.getString(R.string.value_format, String.format("%.4f", currency.value))

        itemView.setOnClickListener{onItemClick(currency)}
    }
}