package com.towertex.erstemodel.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.towertex.erstemodel.model.AccountItem
import com.towertex.erstemodel.model.TransactionItem

@Database(
    entities = [
        AccountItem::class,
        TransactionItem::class
    ],
    version = 2,
    exportSchema = false
)
abstract class ErsteDatabase: RoomDatabase() {
    abstract val ersteDao: ErsteDao

    companion object {
        private const val DATABASE_NAME = "ersteDatabase"

        fun buildDatabase(context: Context): ErsteDatabase = Room
            .databaseBuilder(context, ErsteDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}