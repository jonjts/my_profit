package br.com.jonjts.myprofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.jonjts.myprofit.util.Util
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class HomeFragment : BaseFragment() {

    override fun reload() {
        if (!isAdded) {
            return
        }
        load()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myView: View = inflater.inflate(
            R.layout.fragment_home, container,
            false
        )

        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun initComponents() {
        super.initComponents()
        load()
    }

    fun updateLabels(entrada: Double, saida: Double, lucro: Double) {
        lbl_total_entrada.text = Util.DINHEIRO_REAL.format(entrada)
        lbl_total_saida.text = Util.DINHEIRO_REAL.format(saida)
        lbl_total_lucro.text = Util.DINHEIRO_REAL.format(lucro)
    }


    fun load() {
        var sumEntrada = App.database?.billDao()!!.sumEntrada(
            Util.firstDate(getSelectedMes(), getSelectedAno()),
            Util.lastDate(getSelectedMes(), getSelectedAno())
        )
        var sumSaida = App.database?.billDao()!!.sumSaida(
            Util.firstDate(getSelectedMes(), getSelectedAno()),
            Util.lastDate(getSelectedMes(), getSelectedAno())
        )
        var sumLucro = sumEntrada - sumSaida

        updateLabels(sumEntrada, sumSaida, sumLucro)

        val entries = mutableListOf<PieEntry>()
        entries.add(PieEntry(sumEntrada.toFloat(), getString(R.string.total_entrada)))
        entries.add(PieEntry(sumSaida.toFloat(), getString(R.string.total_saida)))
        entries.add(PieEntry(sumLucro.toFloat(), getString(R.string.total_lucro)))

        val dataSet = PieDataSet(entries, "Total")

        val pieData = PieData()
        pieData.dataSet = dataSet


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
