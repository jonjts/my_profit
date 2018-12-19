package br.com.jonjts.myprofit

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import br.com.jonjts.myprofit.entity.Bill
import com.facebook.stetho.common.Util
import kotlinx.android.synthetic.main.activity_profit.*
import java.text.NumberFormat
import java.util.*


open class BillInsertActivity : AppCompatActivity(), OnDateSetListener {

    val calendar = Calendar.getInstance()

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

        updateDateButton()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun onDateClicked(v: View) {
        var dialog = DatePickerDialog(
            this,
            AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this,
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        updateDate(year, month, dayOfMonth)
    }

    fun updateDate(d: Date) {
        calendar.time = d
        updateDateButton()
    }

    private fun updateDate(year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateDateButton()
    }

    private fun updateDateButton() {
        val convert = br.com.jonjts.myprofit.util.Util.convert(calendar.time)
        btn_dataregistro.text = convert
    }

    open fun onSaveClicked(v: View) {
        if (isValid()) {
            insert()
            setResult(Activity.RESULT_OK)
            Toast.makeText(this, getText(R.string.success_insert), Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun insert() {
        App.database?.billDao()?.insert(bind())
    }

    open fun bind(): Bill {
        val bill = Bill(
            null, txt_descricao.text.toString(),
            convert(txt_saida.text.toString()),
            convert(txt_entrada.text.toString()),
            calendar.time
        )
        return bill
    }

    private fun convert(value: String): Double {
        if (!value.isNullOrBlank()) {
            var replace = value
            replace = replace.replace("R$", "")
            replace = replace.replace(".", "")
            replace = replace.replace(",", ".")
            return replace.toDouble()
        }
        return .0
    }


    fun isValid(): Boolean {
        if (txt_descricao.text.isNullOrBlank()) {
            txt_descricao.error = getString(R.string.required_field)
            return false
        }
        return true
    }


    fun init() {
        applyMoneyMask()
    }

    fun applyMoneyMask() {
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
            ) {
            }
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
            ) {
            }
        })
    }

}
