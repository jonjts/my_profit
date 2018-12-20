package br.com.jonjts.myprofit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jonjts.myprofit.adapter.BillAdapter
import br.com.jonjts.myprofit.adapter.RecyclerItemClickListenr
import br.com.jonjts.myprofit.callback.BillListCallback
import br.com.jonjts.myprofit.entity.Bill
import kotlinx.android.synthetic.main.fragment_bill_list.view.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [BillsFragment.OnListFragmentInteractionListener] interface.
 */
class BillsFragment : BaseFragment(), BillListCallback {


    var billList: RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bill_list, container, false)

        init(view)

        return view
    }

    override fun init(v: View) {
        super.init(v)
        billList = v.list

        with(v.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = BillAdapter(mutableListOf())
            itemAnimator = DefaultItemAnimator()
            addOnItemTouchListener(
                RecyclerItemClickListenr(context,
                    this, object : RecyclerItemClickListenr.OnItemClickListener {

                        override fun onItemClick(view: View, position: Int) {
                            onBillClicked(view.tag as Bill)
                        }

                        override fun onItemLongClick(view: View?, position: Int) {

                        }
                    })
            )
        }

        reload()
    }

    override fun reload() {
        billList?.adapter = BillAdapter(
            App.database?.billDao()!!.getAll()
        )
    }

    override fun onBillClicked(bill: Bill) {
        var it = Intent(activity, BillUpdateActivity::class.java)
        val b = Bundle()
        b.putLong("id", bill?.id!!)
        it.putExtras(b)
        startActivityForResult(it, MainActivity.openBillActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.openBillActivity &&
            resultCode == Activity.RESULT_OK
        ) {
            (activity as MainActivity).reloadFragments()
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance() = BillsFragment()
    }
}
