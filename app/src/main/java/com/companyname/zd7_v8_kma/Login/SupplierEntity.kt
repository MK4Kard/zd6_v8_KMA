package com.companyname.zd7_v8_kma.Login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suppliers")
data class SupplierEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("loginId") val login: Int,
    @ColumnInfo("detail")val detail: String,
    @ColumnInfo("characteristicDetail") val characteristics: String,
    @ColumnInfo("countDetail") val count: Int,
    @ColumnInfo("priceOneDetail") val price: Double,
    @ColumnInfo("buyer") val buyer: String,
    @ColumnInfo("numberBuyers") val num: Int
)