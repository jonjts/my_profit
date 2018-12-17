package br.com.jonjts.myprofit.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.jonjts.myprofit.entity.Bill

@Database(entities = arrayOf(Bill::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun profitDao() : BillDao
}