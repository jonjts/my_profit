package br.com.jonjts.myprofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.filter.view.*
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_header.view.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : androidx.fragment.app.Fragment() {

    var calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myView: View = inflater.inflate(R.layout.fragment_home, container, false)
        myView.btn_filter.setOnClickListener {

        }

        initSpinners(myView)

        return myView
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
        view.spinner_ano.adapter = adapter_years

        view.spinner_ano.setSelection(currentYearPositionInSpinner())
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
        view.spinner_mes.adapter = adapter_months

        view.spinner_mes.setSelection(calendar.get(Calendar.MONTH))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
