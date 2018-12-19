package br.com.jonjts.myprofit

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.view.CollapsibleActionView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_header.view.*
import java.util.*

open class BaseFragment : Fragment(){

    var calendar = Calendar.getInstance()
    var toolBar: CollapsingToolbarLayout? = null


    fun init(v: View){
        toolBar = v!!.collaps_toolbar
        initSpinners(v)
    }

    fun initSpinners(view: View){
        initSpinnerMonth(view)
        initSpinnerYear(view)
    }

    fun initSpinnerYear(view: View){
        val adapter_years = ArrayAdapter.createFromResource(
            context,
            R.array.years,
            R.layout.spinner_item_year
        )

        adapter_years.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        var spinnerAno = view.spinner_ano
        spinnerAno.adapter = adapter_years


        spinnerAno.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onAnoSelected(spinnerAno)
            }
        }

        spinnerAno.setSelection(currentYearPositionInSpinner())
    }

    protected fun onMesSelected(spn: Spinner){
        toolBar!!.title = spn.selectedItem.toString()
    }

    protected fun onAnoSelected(spn: Spinner){

    }

    fun currentYearPositionInSpinner() : Int{
        val years = resources.getStringArray(R.array.years)
        var currentYear = calendar.get(Calendar.YEAR).toString()
        for ((index, value)  in years.withIndex()){
            if (value.equals(currentYear)){
                return index
            }
        }
        return 0
    }


    fun initSpinnerMonth(view: View){
        val adapter_months = ArrayAdapter.createFromResource(
            context,
            R.array.months,
            R.layout.spinner_item_month
        )

        adapter_months.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        val spinner_mes = view.spinner_mes
        spinner_mes.adapter = adapter_months

        spinner_mes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onMesSelected(spinner_mes)
            }
        }
        spinner_mes.setSelection(calendar.get(Calendar.MONTH))

    }
}