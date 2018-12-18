package br.com.jonjts.myprofit

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_profit.*
import android.text.Editable
import android.text.TextWatcher
import java.text.NumberFormat
import java.util.*


class ProfitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profit)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = window.decorView
            decorView.systemUiVisibility = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        init()
    }


    fun init(){
        applyMoneyMask()
    }

    fun applyMoneyMask(){
        txt_entrada.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                if (s.toString() != current) {
                    txt_entrada.removeTextChangedListener(this)

                    val cleanString = s.toString().replace("[R$,.]".toRegex(), "")

                    val parsed = java.lang.Double.parseDouble(cleanString)
                    val formated = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed / 100)

                    current = formated
                    txt_entrada.setText(formated)
                    txt_entrada.setSelection(formated.length)

                    txt_entrada.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {}
        })

        txt_saida.addTextChangedListener(object : TextWatcher {
            private var current = ""

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                if (s.toString() != current) {
                    txt_saida.removeTextChangedListener(this)

                    val cleanString = s.toString().replace("[R$,.]".toRegex(), "")

                    val parsed = java.lang.Double.parseDouble(cleanString)
                    val formated = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed / 100)

                    current = formated
                    txt_saida.setText(formated)
                    txt_saida.setSelection(formated.length)

                    txt_saida.addTextChangedListener(this)
                }
            }

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navigation_profit, menu)
        return true
    }
}
