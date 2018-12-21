package br.com.jonjts.myprofit

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.com.jonjts.myprofit.entity.Bill
import br.com.jonjts.myprofit.util.Util
import kotlinx.android.synthetic.main.activity_profit.*

class BillUpdateActivity : BillInsertActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.title_update)

        loadFields()
    }

    fun loadFields() {
        updateDate(findBill().dataRegistro)
        txt_descricao.setText(findBill().nome)
        txt_entrada.setText(Util.DINHEIRO_REAL.format(findBill().entrada))
        txt_saida.setText(Util.DINHEIRO_REAL.format(findBill().saida))
    }


    fun getBillId(): Long {
        return intent.extras.getLong("id")
    }

    fun findBill(): Bill {
        var id = getBillId()
        return App.database?.billDao()!!.find(id)
    }

    override fun onSaveClicked(v: View) {
        if (isValid()) {
            update()
            setResult(Activity.RESULT_OK)
            Toast.makeText(this, getText(R.string.success_update), Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun update() {
        App.database?.billDao()?.update(bind())
    }

    override fun bind(): Bill {
        var new = super.bind()
        new.id = getBillId()
        return new
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.navigation_delete -> {
                askToDelete()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun askToDelete() {
        var alert = AlertDialog.Builder(this)
        alert.setMessage(getString(R.string.are_you_sure))
        alert.setTitle(R.string.title_delete)
        alert.setPositiveButton(getString(R.string.yes),
            DialogInterface.OnClickListener { dialog, which ->
                App.database?.billDao()?.delete(findBill())
                setResult(Activity.RESULT_OK)
                Toast.makeText(this, getText(R.string.success_remove), Toast.LENGTH_LONG).show()
                finish()
            })
        alert.setCancelable(true)
        alert.setNegativeButton(getString(R.string.no),
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel() })
        alert.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navigation_profit, menu)
        return true
    }
}
