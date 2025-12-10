package com.companyname.zd7_v8_kma.Login

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [LoginEntity::class]
)

abstract class LoginDb:RoomDatabase() {
    abstract fun getLoginDao():LoginDao

    companion object{
        fun getDb(context: Context) : LoginDb{
            return Room.databaseBuilder(context, LoginDb::class.java, "login_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
}