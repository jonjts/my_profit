package br.com.jonjts.myprofit

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import br.com.jonjts.myprofit.util.Util
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.fragment_header.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.File


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

        setHasOptionsMenu(true)

        return inflater.inflate(
            R.layout.fragment_home, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun initComponents() {
        super.initComponents()
        initChart()
    }

    override fun initToolbar() {
        super.initToolbar()
        toolbar_header.menu.findItem(R.id.navigation_share).isVisible = true
    }


    override fun share() {
        val fileName = "chart_" + System.currentTimeMillis()
        val subPath = "MyProfit/image"
        if (chart.saveToGallery(
                fileName, subPath, "myProfit_chart",
                Bitmap.CompressFormat.PNG, 70
            )
        ) {
            val extBaseDir = Environment.getExternalStorageDirectory()
            val file = File(extBaseDir.absolutePath + "/DCIM/" + subPath).path +
                    "/$fileName.png"

            val share = Intent(Intent.ACTION_SEND)
            share.type = "image/png"

            share.putExtra(Intent.EXTRA_STREAM, Uri.parse(file))
            startActivity(Intent.createChooser(share, getString(R.string.title_share)))
        }
    }

    fun initChart() {
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)
        chart.dragDecelerationFrictionCoef = .95f
        chart.setUsePercentValues(true)
        chart.legend.isEnabled = false
    }

    fun updateLabels(entrada: Double, saida: Double, lucro: Double) {
        lbl_total_entrada.text = Util.DINHEIRO_REAL.format(entrada)
        lbl_total_saida.text = Util.DINHEIRO_REAL.format(saida)
        lbl_total_lucro.text = Util.DINHEIRO_REAL.format(lucro)
    }


    fun load() {
        val month = getSelectedMes()
        val year = getSelectedAno()
        var sumEntrada = App.database?.billDao()!!.sumEntrada(
            Util.firstDate(month, year),
            Util.lastDate(month, year)
        )

        var sumSaida = App.database?.billDao()!!.sumSaida(
            Util.firstDate(month, year),
            Util.lastDate(month, year)
        )

        var sumLucro = sumEntrada - sumSaida

        updateLabels(sumEntrada, sumSaida, sumLucro)

        val entries = mutableListOf<PieEntry>()
        entries.add(PieEntry(sumEntrada.toFloat(), getString(R.string.hint_entrada)))
        entries.add(PieEntry(sumSaida.toFloat(), getString(R.string.hint_saida)))
        entries.add(PieEntry(sumLucro.toFloat(), getString(R.string.hint_profit)))

        val dataSet = PieDataSet(entries, "Total")

        var colors = mutableListOf<Int>()
        colors.add(getColor(R.color.entrada))
        colors.add(getColor(R.color.saida))
        colors.add(getColor(R.color.lucro))
        dataSet.colors = colors

        val pieData = PieData()
        pieData.dataSet = dataSet
        pieData.setValueTextColor(Color.WHITE)
        pieData.setValueTextSize(18f)

        chart.data = pieData
        pieData.setValueFormatter(PercentFormatter())
        chart.animateXY(500, 500)
        chart.highlightValues(null)

        chart.invalidate()
    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(activity!!, color)
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
