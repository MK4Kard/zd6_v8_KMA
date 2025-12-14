package com.companyname.zd7_v8_kma.Login

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WorkerDao {
    @Insert
    suspend fun insert(furnitures: WorkerEntity): Long

    @Update
    suspend fun update(furniture: WorkerEntity)

    @Delete
    suspend fun delete(furniture: WorkerEntity)

    @Query("SELECT * FROM furnitures")
    suspend fun getAll(): List<WorkerEntity>

    @Query("SELECT * FROM furnitures WHERE loginId = :loginId")
    suspend fun getByLoginId(loginId: Int): List<WorkerEntity>

    @Query("SELECT * FROM furnitures WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): WorkerEntity

    @Query("SELECT DISTINCT furnitureName FROM furnitures")
    suspend fun getAllDetailNames(): List<String>

    @Query("SELECT * FROM furnitures WHERE furnitureName = :furniture")
    suspend fun getByDetail(furniture: String): List<WorkerEntity>

    @Query("""
        SELECT * FROM furnitures 
        WHERE loginId = :loginId
        AND furnitureName = :name
        AND modelFurniture = :model
        AND priceFurniture = :price
        LIMIT 1
    """)
    suspend fun findSame(
        loginId: Int,
        name: String,
        model: String,
        price: Double
    ): WorkerEntity?
}

@Dao
interface FurnitureDetailDao {

    @Query("""
        SELECT * FROM furniture_details
        WHERE furnitureId = :furnitureId AND detailId = :detailId
        LIMIT 1
    """)
    suspend fun getOne(furnitureId: Int, detailId: Int): FurnitureDetailEntity?

    @Insert
    suspend fun insert(entity: FurnitureDetailEntity)

    @Update
    suspend fun update(entity: FurnitureDetailEntity)

    @Delete
    suspend fun delete(entity: FurnitureDetailEntity)
}

@Dao
interface FurnitureSupplierDao {

    @Insert
    suspend fun insert(fs: FurnitureSupplierEntity)

    @Query("SELECT * FROM furniture_suppliers WHERE furnitureId = :furnitureId")
    suspend fun getByFurniture(furnitureId: Int): List<FurnitureSupplierEntity>

    @Query("SELECT * FROM furniture_suppliers WHERE supplierId = :supplierId")
    suspend fun getBySupplier(supplierId: Int): List<FurnitureSupplierEntity>
}
