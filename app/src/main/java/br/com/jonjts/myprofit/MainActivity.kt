package br.com.jonjts.myprofit

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var fragments = mutableListOf<Fragment>()
    val  fm = getSupportFragmentManager()

    fun selected(f: Fragment){
        if(!f.isVisible) {
            val ft = fm.beginTransaction()
            ft.add(R.id.container, f)
            ft.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(bottombar)
        initFragmentsList()

        fab_add_item.setOnClickListener {
            startActivity(Intent(this, ProfitActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.navigation_home -> selected(fragments[0])
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.navigation, menu)
        return true
    }

    fun initFragmentsList(){
        fragments.add(HomeFragment.newInstance())
        selected(fragments[0])
    }
}
