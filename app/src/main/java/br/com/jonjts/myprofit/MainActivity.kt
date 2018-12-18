package br.com.jonjts.myprofit

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.jonjts.myprofit.R.menu.navigation
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.bottom_sheet.*
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import android.os.Build




class MainActivity : AppCompatActivity() {

    var fragments = mutableListOf<Fragment>()
    val fm = getSupportFragmentManager()

    fun selected(f: Fragment) {
        if (!f.isVisible) {
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
                //message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_itens -> {
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
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
    }

    fun initFragmentsList() {
        fragments.add(HomeFragment.newInstance())
        selected(fragments[0])
    }
}
