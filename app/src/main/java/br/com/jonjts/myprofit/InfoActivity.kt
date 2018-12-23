package br.com.jonjts.myprofit

import android.os.Build
import android.os.Bundle
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Html.fromHtml
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = window.decorView
            decorView.systemUiVisibility = WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

            lbl_thai.text = fromHtml("<a href=\"http://twitter.com/thaiisley\">Thaisley Sayonara</a> ",
                FROM_HTML_MODE_LEGACY
            )

            lbl_me.text = fromHtml("<a href=\"http://twitter.com/jonjts\">@jonjts</a>",
                FROM_HTML_MODE_LEGACY)
        }

        setSupportActionBar(toolbar_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.app_name)
        supportActionBar!!.subtitle = "v. "+ getString(R.string.app_version)

        lbl_thai.movementMethod = LinkMovementMethod.getInstance()

        lbl_me.movementMethod = LinkMovementMethod.getInstance()
    }
}
