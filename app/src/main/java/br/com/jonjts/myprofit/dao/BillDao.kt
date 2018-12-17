package br.com.jonjts.myprofit.dao

import androidx.room.*
import br.com.jonjts.myprofit.entity.Bill

@Dao
interface BillDao{

    @Insert
    fun insert(vararg bill: Bill)

    @Update
    fun update(bill: Bill)

    @Delete
    fun delete(bill: Bill)

    @Query("SELECT * FROM profit WHERE strftime('%m', data_registro) = :month AND strftime('%y', data_registro) = :year")
    fun consultByMonthYear(month: Int, year: Int) : List<Bill>

    @Query("SELECT * FROM profit WHERE id = :id")
    fun find(id: Long) : Bill
}