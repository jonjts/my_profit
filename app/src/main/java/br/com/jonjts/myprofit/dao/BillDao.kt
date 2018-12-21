package br.com.jonjts.myprofit.dao

import androidx.room.*
import br.com.jonjts.myprofit.entity.Bill
import java.util.*

@Dao
interface BillDao{

    @Insert
    fun insert(vararg bill: Bill)

    @Update
    fun update(bill: Bill)

    @Delete
    fun delete(bill: Bill)

    @Query("SELECT * FROM bill WHERE data_registro BETWEEN :begin AND :end ORDER BY data_registro")
    fun consultByMonthYear(begin: Date, end: Date) : List<Bill>

    @Query("SELECT * FROM bill WHERE nome LIKE :nome ORDER BY nome ASC, data_registro DESC LIMIT 30")
    fun consultByNome(nome: String) : List<Bill>


    @Query("SELECT * FROM bill ")
    fun getAll() : List<Bill>

    @Query("SELECT * FROM bill WHERE id = :id")
    fun find(id: Long) : Bill
}