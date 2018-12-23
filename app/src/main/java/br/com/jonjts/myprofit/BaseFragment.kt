package br.com.jonjts.myprofit

import android.app.Activity
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import br.com.jonjts.myprofit.callback.DatabaseCallback
import kotlinx.android.synthetic.main.fragment_header.*
import java.util.*


open abstract class BaseFragment : Fragment(), DatabaseCallback{

    var calendar = Calendar.getInstance()


    override fun onDataChange() {
        reload()
    }

    open abstract fun reload()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.newBillActivity &&
            resultCode == Activity.RESULT_OK
        ) {
            (activity as MainActivity).reloadFragments()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.navigation_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    open fun initComponents(){
        initSpinners()
        initToolbar()
    }

    open fun initToolbar(){
        toolbar_header.inflateMenu(R.menu.navigation_fragment)
        toolbar_header.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.navigation_share -> {
                    share()
                }
                R.id.navigation_info -> {
                    showInfo()
                }
            }
            true
        }
    }

    fun showInfo(){
        startActivity(Intent(activity, InfoActivity::class.java))
    }

    abstract fun share()



    fun initSpinners(){
        initSpinnerMonth()
        initSpinnerYear()
    }

    fun initSpinnerYear(){
        val adapter_years = ArrayAdapter.createFromResource(
            context,
            R.array.years,
            R.layout.spinner_item_year
        )

        adapter_years.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_ano.adapter = adapter_years

        spinner_ano.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                reload()
            }
        }
        spinner_ano?.setSelection(currentYearPositionInSpinner())
    }

    fun getSelectedMes(): Int{
        return spinner_mes.selectedItemPosition
    }

    fun getSelectedAno(): Int{
        return spinner_ano.selectedItem.toString().toInt()
    }

    protected fun onMesSelected(spn: Spinner){
        toolbar_header.title = spn.selectedItem.toString()
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


    fun initSpinnerMonth(){
        val adapter_months = ArrayAdapter.createFromResource(
            context,
            R.array.months,
            R.layout.spinner_item_month
        )

        adapter_months.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mes.adapter = adapter_months

        spinner_mes?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                onMesSelected(spinner_mes)
                reload()
            }
        }
        spinner_mes.setSelection(calendar.get(Calendar.MONTH))

    }
}