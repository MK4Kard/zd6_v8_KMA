package com.companyname.zd7_v8_kma.Login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface SupplierDao {

    @Insert
    suspend fun insert(vararg suppliers: SupplierEntity)

    @Update
    suspend fun update(supplier: SupplierEntity)

    @Delete
    suspend fun delete(supplier: SupplierEntity)

    @Query("SELECT * FROM suppliers")
    suspend fun getAll(): List<SupplierEntity>

    @Query("SELECT * FROM suppliers WHERE detail = :detail")
    suspend fun getByDetail(detail: String): List<SupplierEntity>

    @Query("SELECT * FROM suppliers WHERE loginId = :loginId")
    suspend fun getByLoginId(loginId: Int): List<SupplierEntity>

    @Query("SELECT * FROM suppliers WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): SupplierEntity?

    @Query("SELECT DISTINCT detail FROM suppliers")
    suspend fun getAllDetailNames(): List<String>

    @Query("""
        SELECT * FROM suppliers 
        WHERE loginId = :loginId
        AND detail = :detail
        AND characteristicDetail = :characteristics
        AND priceOneDetail = :price
        LIMIT 1
    """)
    suspend fun findSame(
        loginId: Int,
        detail: String,
        characteristics: String,
        price: Double
    ): SupplierEntity?

    @Query("SELECT DISTINCT detail FROM suppliers")
    fun getUniqueDetails(): List<String>

    @Query("""
        SELECT logins.* FROM logins
        INNER JOIN furniture_suppliers fs ON logins.id = fs.supplierId
        WHERE fs.furnitureId = :furnitureId
    """)
    suspend fun getBuyersByFurniture(furnitureId: Int): List<LoginEntity>
}