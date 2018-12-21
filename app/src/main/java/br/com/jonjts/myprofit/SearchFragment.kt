package br.com.jonjts.myprofit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.jonjts.myprofit.adapter.SearchAdapter
import br.com.jonjts.myprofit.callback.DatabaseCallback
import br.com.jonjts.myprofit.entity.Bill
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_search.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment(), DatabaseCallback {

    private val listener: OnListFragmentInteractionListener
    var txtSearch: TextInputEditText? = null
    var list: RecyclerView? = null

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Bill)
    }


    override fun onDataChange() {
        if (!isAdded) {
            return
        }
        load()

    }

    init {
        listener = object : OnListFragmentInteractionListener {
            override fun onListFragmentInteraction(item: Bill) {
                var it = Intent(activity, BillUpdateActivity::class.java)
                val b = Bundle()
                b.putLong("id", item?.id!!)
                it.putExtras(b)
                startActivityForResult(it, MainActivity.openBillActivity)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_search, container, false)
        list = inflate.list
        with(inflate.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = SearchAdapter(mutableListOf(), listener)
        }
        initTextField(inflate)
        return inflate
    }

    fun initTextField(v: View) {
        txtSearch = v.txt_search
        txtSearch?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                load()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.openBillActivity &&
            resultCode == Activity.RESULT_OK
        ) {
            (activity as MainActivity).reloadFragments()
        }
    }


    private fun load() {
        var text = txtSearch?.text.toString()
        if (text.isNullOrBlank()) {
            list!!.adapter = SearchAdapter(mutableListOf(), listener)
        } else {
            list!!.adapter = SearchAdapter(App.database?.billDao()!!.consultByNome("%" + text + "%"), listener)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
