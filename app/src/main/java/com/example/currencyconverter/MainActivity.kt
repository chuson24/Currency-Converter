package com.example.currencyconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtAmount: EditText
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var txtConvertedAmount: TextView
    private lateinit var txtExchangeRate: TextView

    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "VND" to 23185.0,
        "EUR" to 0.91,
        "JPY" to 150.0,
        "GBP" to 0.76
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtAmount = findViewById(R.id.edtAmount)
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        txtConvertedAmount = findViewById(R.id.txtConvertedAmount)
        txtExchangeRate = findViewById(R.id.txtExchangeRate)

        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        spinnerFrom.setSelection(0) // Mặc định chọn USD
        spinnerTo.setSelection(1) // Mặc định chọn VND

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                updateConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                updateConversion()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        edtAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateConversion()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateConversion() {
        val fromCurrency = spinnerFrom.selectedItem.toString()
        val toCurrency = spinnerTo.selectedItem.toString()
        val amountStr = edtAmount.text.toString()

        if (amountStr.isEmpty()) {
            txtConvertedAmount.text = "0"
            return
        }

        val amount = amountStr.toDouble()
        val fromRate = exchangeRates[fromCurrency] ?: return
        val toRate = exchangeRates[toCurrency] ?: return

        val convertedAmount = (amount / fromRate) * toRate
        txtConvertedAmount.text = String.format("%.2f", convertedAmount)

        txtExchangeRate.text = "1 $fromCurrency = ${String.format("%.2f", toRate / fromRate)} $toCurrency"
    }
}
