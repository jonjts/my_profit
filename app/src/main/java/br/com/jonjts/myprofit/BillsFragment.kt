package br.com.jonjts.myprofit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jonjts.myprofit.adapter.BillAdapter
import br.com.jonjts.myprofit.adapter.RecyclerItemClickListenr
import br.com.jonjts.myprofit.callback.BillListCallback
import br.com.jonjts.myprofit.entity.Bill
import br.com.jonjts.myprofit.util.Util
import kotlinx.android.synthetic.main.fragment_bill_list.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [BillsFragment.OnListFragmentInteractionListener] interface.
 */
class BillsFragment : BaseFragment(), BillListCallback {
    override fun share() {

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bill_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    override fun initComponents() {
        super.initComponents()

        with(list) {
            layoutManager = LinearLayoutManager(context)
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
        if (!isAdded) {
            return
        }
        val month = getSelectedMes()
        val year = getSelectedAno()
        val mValues = App.database?.billDao()!!.consultByMonthYear(
            Util.firstDate(month, year),
            Util.lastDate(month, year)
        )
        list.adapter = BillAdapter(mValues, lnl_empty, list)
    }

    override fun onBillClicked(bill: Bill) {
        var it = Intent(activity, BillUpdateActivity::class.java)
        val b = Bundle()
        b.putLong("id", bill?.id!!)
        it.putExtras(b)
        startActivityForResult(it, MainActivity.editBillActivity)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var action = if (data != null) data.action else null
        (activity as MainActivity).onActivityResult(
            requestCode,
            resultCode, action
        )
    }

    companion object {

        @JvmStatic
        fun newInstance() = BillsFragment()
    }
}
