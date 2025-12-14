package com.companyname.zd7_v8_kma.Login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class ClientEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("loginId") val login: Int,
    @ColumnInfo("orderId")val orderId: Int,
    @ColumnInfo("orderCompany") val company: Int,
    @ColumnInfo("orderCount") val count: Int,
    @ColumnInfo("priceOrder") val price: Double
)