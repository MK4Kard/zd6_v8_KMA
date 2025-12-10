package com.companyname.zd7_v8_kma.Login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logins")
data class LoginEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("mail")val mail: String,
    @ColumnInfo("login") val login: String,
    @ColumnInfo("password") val password: String,
    @ColumnInfo("user") val user: String
)