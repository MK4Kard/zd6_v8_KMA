package com.companyname.zd7_v8_kma.Login

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ClientDao {

    @Insert
    suspend fun insert(client: ClientEntity): Long

    @Update
    suspend fun update(client: ClientEntity)

    @Delete
    suspend fun delete(client: ClientEntity)

    // Все клиенты
    @Query("SELECT * FROM clients")
    suspend fun getAll(): List<ClientEntity>

    // Клиенты по пользователю (loginId)
    @Query("SELECT * FROM clients WHERE loginId = :loginId")
    suspend fun getByLoginId(loginId: Int): List<ClientEntity>

    // Клиенты по заказу
    @Query("SELECT * FROM clients WHERE `orderId` = :orderId")
    suspend fun getByOrder(orderId: Int): List<ClientEntity>

    // Один клиент по id
    @Query("SELECT * FROM clients WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ClientEntity?

    @Query("SELECT * FROM clients WHERE orderCompany = :workerId")
    suspend fun getClientsByWorker(workerId: Int): List<ClientEntity>
}