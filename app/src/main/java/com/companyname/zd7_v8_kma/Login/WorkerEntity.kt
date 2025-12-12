package com.companyname.zd7_v8_kma.Login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workers")
data class WorkerEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("loginId") val login: Int,
    @ColumnInfo("furnitureName") val furniture: String,
    @ColumnInfo("modelFurniture") val model: String,
    @ColumnInfo("furnitureCount") val count: Int,
    @ColumnInfo("spendingDetails") val spend: Double,
    @ColumnInfo("supplier") val supplier: String,
    @ColumnInfo("priceFurniture") val price: Double
)