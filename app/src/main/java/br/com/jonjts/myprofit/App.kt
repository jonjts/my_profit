package br.com.jonjts.myprofit

import android.app.Application
import androidx.room.Room
import br.com.jonjts.myprofit.dao.AppDatabase
import com.facebook.stetho.Stetho

class App : Application() {

    companion object {
        var database : AppDatabase? = null
    }


    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            AppDatabase::class.java, "myprofit").
            allowMainThreadQueries().build()

        //Stetho
        val initializerBuilder = Stetho.newInitializerBuilder(this)
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
        val initializer = initializerBuilder.build()
        Stetho.initialize(initializer)
    }
}