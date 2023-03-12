package com.ukadovlad21.numbersapitesttask.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ukadovlad21.numbersapitesttask.model.NumberData

@Dao
interface NumbersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(numberData: NumberData): Long


    @Query("SELECT DISTINCT * FROM numbers ORDER BY id DESC")
    fun getAllNumbersDataLiveData(): LiveData<List<NumberData>>



}
