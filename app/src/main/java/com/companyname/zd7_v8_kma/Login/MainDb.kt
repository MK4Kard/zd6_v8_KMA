package com.companyname.zd7_v8_kma.Login

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.jvm.java

@Database(
    version = 3,
    entities = [
        LoginEntity::class,
        SupplierEntity::class,
        WorkerEntity::class,
        FurnitureDetailEntity::class,
        FurnitureSupplierEntity::class,
        ClientEntity::class
    ]
)

abstract class MainDb:RoomDatabase() {
    abstract fun getLoginDao(): LoginDao
    abstract fun getSupplierDao(): SupplierDao
    abstract fun getWorkerDao(): WorkerDao
    abstract fun getFurnitureDetailDao(): FurnitureDetailDao
    abstract fun getFurnitureSupplierDao(): FurnitureSupplierDao
    abstract fun getClientDao(): ClientDao

    companion object {
        @Volatile private var INSTANCE: MainDb? = null

        fun getDb(context: Context): MainDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDb::class.java,
                    "main.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}