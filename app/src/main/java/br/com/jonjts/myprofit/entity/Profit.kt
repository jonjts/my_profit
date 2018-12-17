package br.com.jonjts.myprofit.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Profit(
    @PrimaryKey var id: Long,
    @ColumnInfo var nome: String,
    @ColumnInfo var saida: Double,
    @ColumnInfo var entrada: Double,
    @ColumnInfo(name = "data_registro") var dataRegistro: Date
)