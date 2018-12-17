package br.com.jonjts.myprofit.dao

import androidx.room.*
import br.com.jonjts.myprofit.entity.Profit
import java.util.*

@Dao
interface ProfitDao{

    @Insert
    fun insert(vararg profit: Profit)

    @Update
    fun update(profit: Profit)

    @Delete
    fun delete(profit: Profit)

    @Query("SELECT * FROM profit WHERE strftime('%m', data_registro) = :month AND strftime('%y', data_registro) = :year")
    fun consultByMonthYear(month: Int, year: Int) : List<Profit>

    @Query("SELECT * FROM profit WHERE id = :id")
    fun find(id: Long) : Profit
}