package com.ukadovlad21.numbersapitesttask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ukadovlad21.numbersapitesttask.model.NumberData


@Database(
    entities = [NumberData::class],
    version = 1
)
abstract class NumbersDatabase : RoomDatabase() {

    abstract fun getNumbersDao(): NumbersDao

    companion object {
        @Volatile
        private var instance: NumbersDatabase? = null

        operator fun invoke(context: Context): NumbersDatabase {
            return instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, NumbersDatabase::class.java, "saved_numbers_db.db"
        ).fallbackToDestructiveMigration().build()

    }

}
