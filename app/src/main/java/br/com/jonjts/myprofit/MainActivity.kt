package br.com.jonjts.myprofit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import br.com.jonjts.myprofit.callback.DatabaseCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var fragments = mutableListOf<Fragment>()
    val fm = getSupportFragmentManager()


    companion object {
        val newBillActivity = 1891
        val editBillActivity = 2891
    }

    fun selected(f: Fragment) {
        if (!f.isVisible) {
            for (fragment in supportFragmentManager.fragments) {
                supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
            val ft = fm.beginTransaction()
            ft.add(R.id.container, f)
            ft.commit()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                selected(fragments[0])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                selected(fragments[1])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_itens -> {
                selected(fragments[2])
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun giveMePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            // Se não tiver permissão, peça
            var alert = AlertDialog.Builder(this)
            alert.setMessage(getString(R.string.text_permission))
            alert.setTitle(R.string.title_permission)
            alert.setPositiveButton(
                getString(R.string.yes)
            ) { dialog, which ->
                ActivityCompat.requestPermissions(
                    this,
                    mutableListOf(Manifest.permission.WRITE_EXTERNAL_STORAGE).toTypedArray(),
                    19
                )
            }
            alert.setCancelable(true)
            alert.setNegativeButton(
                getString(R.string.no)
            ) { dialog, which ->
                dialog.cancel()
            }
            alert.create().show()

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragmentsList()
        navegation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = window.decorView
            decorView.systemUiVisibility = FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }

        giveMePermission()
    }

    fun onAddBillClicked(v: View) {
        startActivityForResult(
            Intent(this, BillInsertActivity::class.java),
            newBillActivity
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResult(requestCode, resultCode)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: String? = null) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                MainActivity.editBillActivity -> {
                    var message =
                        if (data.isNullOrBlank()) getString(R.string.success_update) else getString(R.string.success_remove)
                    Snackbar.make(
                        btn_open_bill, message, Snackbar.LENGTH_LONG
                    ).show()
                }
                MainActivity.newBillActivity -> {
                    val make = Snackbar.make(
                        btn_open_bill,
                        getString(R.string.success_insert), Snackbar.LENGTH_LONG
                    )
                    make.show()

                }
            }
            reloadFragments()
        }
    }

    fun reloadFragments() {
        for (f in fragments) (f as DatabaseCallback).onDataChange()
    }

    fun initFragmentsList() {
        fragments.add(HomeFragment.newInstance())
        fragments.add(SearchFragment.newInstance())
        fragments.add(BillsFragment.newInstance())
        selected(fragments[0])
    }
}
