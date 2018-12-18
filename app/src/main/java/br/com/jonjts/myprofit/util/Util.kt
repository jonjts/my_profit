package br.com.jonjts.myprofit.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*


class Util{

    private val BRAZIL = Locale("pt", "BR")
    private val REAL = DecimalFormatSymbols(BRAZIL)
    val DINHEIRO_REAL = DecimalFormat("¤ ###,###,##0.00", REAL)

    fun convert(date: Date): String {
        val dateInstance = SimpleDateFormat.getDateInstance()
        return dateInstance.format(date)
    }

    fun fix(date: Date): Date {
        date.setHours(0)
        date.setMinutes(0)
        date.setSeconds(0)
        return date
    }
}