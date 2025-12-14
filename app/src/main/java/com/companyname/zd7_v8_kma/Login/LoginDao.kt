package com.companyname.zd7_v8_kma.Login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoginDao {
    @Query("SELECT * FROM logins")
    suspend fun getAll(): List<LoginEntity>

    @Query("SELECT * FROM logins WHERE mail = :mail LIMIT 1")
    suspend fun getByMail(mail: String): LoginEntity?

    @Query("SELECT * FROM logins WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): LoginEntity?

    @Insert
    suspend fun insert(vararg logins: LoginEntity)
}