package com.companyname.zd7_v8_kma.Login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LoginDao {
    @Query("SELECT * FROM logins")
    fun getAll(): List<LoginEntity>

    @Insert
    fun insert(vararg logins: LoginEntity)
}