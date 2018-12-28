package br.com.jonjts.myprofit

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import androidx.room.Room
import br.com.jonjts.myprofit.dao.AppDatabase
import br.com.jonjts.myprofit.entity.Bill
import br.com.jonjts.myprofit.util.Util
import com.facebook.stetho.Stetho
import org.jetbrains.anko.doAsync
import java.io.File
import java.util.*

class App : Application() {

    val BACKUP_TAG = "OLD_BACKUP"

    companion object {
        var database: AppDatabase? = null
    }


    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "myprofit"
        ).allowMainThreadQueries().build()

        //Stetho
        val initializerBuilder = Stetho.newInitializerBuilder(this)
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
        val initializer = initializerBuilder.build()
        Stetho.initialize(initializer)

        checkOldBackup()
    }

    private fun checkOldBackup() {
        if (existOldBackup()) {
            Log.d(BACKUP_TAG, "Achou o arquivo de backup")
            startRestore()
        }
    }


    private fun startRestore() {
        doAsync {
            Log.d(BACKUP_TAG, "Abrindo conexão com o banco")
            val openDatabase = SQLiteDatabase.openDatabase(
                Util.OLD_BACKUP,
                null, SQLiteDatabase.CREATE_IF_NECESSARY
            )

            Log.d(BACKUP_TAG, "Rodando query")
            val rawQuery = openDatabase.rawQuery("SELECT * FROM receita", null)

            while (rawQuery.moveToNext()) {
                val nome = rawQuery.getString(rawQuery.getColumnIndex("nome"))
                val entrada = rawQuery.getDouble(rawQuery.getColumnIndex("entrada"))
                val saida = rawQuery.getDouble(rawQuery.getColumnIndex("saida"))
                val data = rawQuery.getLong(rawQuery.getColumnIndex("dtEntrada"))

                val bill = Bill(
                    nome = nome, entrada = entrada,
                    saida = saida, dataRegistro = Date(data)
                )

                Log.d(BACKUP_TAG, "Adicionando nova bill $bill")
                database?.billDao()!!.insert(bill)
            }
            Log.d(BACKUP_TAG, "Fechando conexão")
            openDatabase.close()
            renameOldBackup()
        }
    }

    // Verifica se o arquivo de backup exite
    private fun existOldBackup(): Boolean {
        val f = File(Util.OLD_BACKUP)
        return f.exists()
    }

    //Renomeia o arquivo antigo para não ser restaurado novamento
    private fun renameOldBackup() {
        val file = File(Util.OLD_BACKUP)
        val newFile = File(Util.OLD_BACKUP+".old")
        Log.d(BACKUP_TAG, "Renomeando arquivo de backup")
        file.renameTo(newFile)
    }
}