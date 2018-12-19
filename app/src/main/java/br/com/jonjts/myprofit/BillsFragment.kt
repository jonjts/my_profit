package br.com.jonjts.myprofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jonjts.myprofit.adapter.BillRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_bill_list.view.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [BillsFragment.OnListFragmentInteractionListener] interface.
 */
class BillsFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bill_list, container, false)


            with(view.list) {
                layoutManager = LinearLayoutManager(context)
                adapter = BillRecyclerViewAdapter(App.database?.billDao()!!.getAll())
            }

        init(view)
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance() = BillsFragment()
    }
}
