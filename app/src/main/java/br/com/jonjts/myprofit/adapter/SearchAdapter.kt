package br.com.jonjts.myprofit.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.jonjts.myprofit.R
import br.com.jonjts.myprofit.SearchFragment
import br.com.jonjts.myprofit.entity.Bill
import br.com.jonjts.myprofit.util.Util
import kotlinx.android.synthetic.main.fragment_search_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class SearchAdapter(
    private val mValues: List<Bill>,
    private val mListener: SearchFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Bill
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mNome.text = item.nome
        holder.mData.text = Util.convertsShort(item.dataRegistro)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNome: TextView = mView.lbl_nome
        val mData: TextView = mView.lbl_data

    }
}
