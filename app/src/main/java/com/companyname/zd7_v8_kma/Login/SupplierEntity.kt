package com.companyname.zd7_v8_kma.Login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suppliers")
data class SupplierEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("loginId") val login: Int,
    @ColumnInfo("detail") var detail: String,
    @ColumnInfo("characteristicDetail") var characteristics: String,
    @ColumnInfo("countDetail") var count: Int,
    @ColumnInfo("priceOneDetail") var price: Double
)