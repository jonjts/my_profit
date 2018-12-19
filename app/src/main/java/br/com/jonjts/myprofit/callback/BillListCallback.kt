package br.com.jonjts.myprofit.callback

import br.com.jonjts.myprofit.entity.Bill

interface BillListCallback{

    fun onBillClicked(bill: Bill)
}