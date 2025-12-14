package com.companyname.zd7_v8_kma.Login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "furnitures")
data class WorkerEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("loginId") val login: Int,
    @ColumnInfo("furnitureName") var furniture: String,
    @ColumnInfo("modelFurniture") var model: String,
    @ColumnInfo("furnitureCount") var count: Int,
    @ColumnInfo("priceFurniture") var price: Double
)

@Entity(
    tableName = "furniture_details",
    primaryKeys = ["furnitureId", "detailId"]
)
data class FurnitureDetailEntity(
    val furnitureId: Int,
    val detailId: Int,
    val count: Int
)

@Entity(
    tableName = "furniture_suppliers",
    primaryKeys = ["furnitureId", "supplierId"]
)
data class FurnitureSupplierEntity(
    val furnitureId: Int,
    val supplierId: Int
)