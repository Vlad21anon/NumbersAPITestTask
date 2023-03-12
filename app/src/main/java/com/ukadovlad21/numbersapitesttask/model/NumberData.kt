package com.ukadovlad21.numbersapitesttask.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "numbers",
    indices = [Index(value = ["text"], unique = true)]
)
data class NumberData(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    val found: Boolean,
    val number: Int,
    val text: String,
    val type: String
)