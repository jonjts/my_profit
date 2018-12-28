package br.com.jonjts.myprofit.util

import android.os.Environment
import java.io.File
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*


class Util {


    companion object {
        val OLD_BACKUP =
            Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + "MyProfit" + File.separator + "backup" +
                    File.separator + File.separator + "myprofit.db"

        private val BRAZIL = Locale("pt", "BR")
        private val REAL = DecimalFormatSymbols(BRAZIL)
        val DINHEIRO_REAL = DecimalFormat("¤ ###,###,##0.00", REAL)

        //Retorna o primeiro dia do mês
        fun firstDate(month: Int, year: Int): Date {
            var c = Calendar.getInstance()
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, 1)
            return c.time
        }

        //Retorna a data com o ultimo dia do mes
        fun lastDate(month: Int, year: Int): Date {
            var c = Calendar.getInstance()
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH))
            return c.time
        }


        fun convert(date: Date): String {
            val dateInstance = SimpleDateFormat.getDateInstance()
            return dateInstance.format(date)
        }

        fun convertsShort(date: Date): String {
            val dateInstance = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT)
            return dateInstance.format(date)
        }

        fun fix(date: Date): Date {
            date.setHours(0)
            date.setMinutes(0)
            date.setSeconds(0)
            return date
        }
    }


}