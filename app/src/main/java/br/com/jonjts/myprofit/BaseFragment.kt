package br.com.jonjts.myprofit

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import br.com.jonjts.myprofit.callback.DatabaseCallback
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.fragment_header.*
import kotlinx.android.synthetic.main.fragment_header.view.*
import java.util.*

open abstract class BaseFragment : Fragment(), DatabaseCallback{


    var calendar = Calendar.getInstance()
    var toolBar: CollapsingToolbarLayout? = null
    var spinnerAno: Spinner? = null
    var spinnerMes: Spinner? = null

    override fun onDataChange() {
        reload()
    }

    open abstract fun reload()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.openBillActivity &&
            resultCode == Activity.RESULT_OK
        ) {
            (activity as MainActivity).reloadFragments()
        }
    }

    open fun init(v: View){
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
        spinnerAno = view.spinner_ano
        spinnerAno?.adapter = adapter_years

        spinnerAno?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                reload()
            }
        }
        spinnerAno?.setSelection(currentYearPositionInSpinner())
    }

    fun getSelectedMes(): Int{
        return spinnerMes!!.selectedItemPosition
    }

    fun getSelectedAno(): Int{
        return spinnerAno?.selectedItem.toString().toInt()
    }

    protected fun onMesSelected(spn: Spinner){
        toolBar!!.title = spn.selectedItem.toString()
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
        spinnerMes = view.spinner_mes
        spinnerMes?.adapter = adapter_months

        spinnerMes?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onMesSelected(spinner_mes)
                reload()
            }
        }
        spinnerMes?.setSelection(calendar.get(Calendar.MONTH))

    }
}