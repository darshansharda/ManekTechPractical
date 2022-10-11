package com.manektechpractical.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TransactionDetails::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {

    companion object {

        private var databaseInstance: TransactionDatabase? = null
        private const val dbName = "transaction_database"

        fun getDatabaseInstance(context: Context): TransactionDatabase? {
            databaseInstance = Room.databaseBuilder(
                context,
                TransactionDatabase::class.java,
                dbName
            ).fallbackToDestructiveMigration().build()

            return databaseInstance
        }

    }

    abstract fun getTransactionDao(): TransactionDao
}