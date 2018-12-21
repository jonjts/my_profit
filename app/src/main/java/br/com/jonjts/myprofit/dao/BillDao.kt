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

    @Query("SELECT * FROM bill WHERE strftime('%m', data_registro) = :month AND strftime('%Y', data_registro) = :year ORDER BY data_registro")
    fun consultByMonthYear(month: Int, year: Int) : List<Bill>


    @Query("SELECT * FROM bill ")
    fun getAll() : List<Bill>

    @Query("SELECT * FROM bill WHERE id = :id")
    fun find(id: Long) : Bill
}