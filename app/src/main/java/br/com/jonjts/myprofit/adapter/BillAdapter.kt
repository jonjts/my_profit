package br.com.jonjts.myprofit.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.jonjts.myprofit.R
import br.com.jonjts.myprofit.callback.BillListCallback
import br.com.jonjts.myprofit.dummy.DummyContent.DummyItem
import br.com.jonjts.myprofit.entity.Bill
import br.com.jonjts.myprofit.util.Util
import kotlinx.android.synthetic.main.fragment_bill.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class BillAdapter(
    val mValues: List<Bill>,
    val callback: BillListCallback
) :
    RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Bill

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_bill, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mDescricao.text = item.nome
        holder.mDataRegistro.text = Util.convertsShort(item.dataRegistro)

        if (!item.entrada.isNaN())
            holder.mEntrada.text = Util.DINHEIRO_REAL.format(item.entrada)
        else
            holder.mPanelEntrada.visibility = View.GONE

        if (!item.saida.isNaN())
            holder.mSaida.text = Util.DINHEIRO_REAL.format(item.saida)
        else
            holder.mPanelSaida.visibility = View.GONE


        if (item.entrada > .0 && item.saida > .0) {
            var lucro = item.entrada - item.saida

            holder.mLucro.visibility = View.VISIBLE
            holder.mLucro.text = Util.DINHEIRO_REAL.format(lucro)


            if (lucro >= 0)
                holder.mLucro.setTextColor(Color.parseColor("#ff3ba311"))
            else
                holder.mLucro.setTextColor(Color.parseColor("#ff96281a"))
        }

        with(holder.mView) {
            tag = item
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mDescricao: TextView = mView.lbl_title
        val mDataRegistro: TextView = mView.lbl_dataregistro
        val mEntrada: TextView = mView.lbl_entrada
        val mSaida: TextView = mView.lbl_saida
        val mLucro: TextView = mView.lbl_lucro

        val mPanelEntrada: LinearLayout = mView.linear_entrada
        val mPanelSaida: LinearLayout = mView.linear_saida


        override fun toString(): String {
            return super.toString() + " '" + mDataRegistro.text + "'"
            return ""
        }
    }
}
