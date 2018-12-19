package br.com.jonjts.myprofit.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.jonjts.myprofit.entity.Bill

@Database(entities = arrayOf(Bill::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun billDao() : BillDao
}