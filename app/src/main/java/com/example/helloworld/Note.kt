package com.example.helloworld

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // <-- Bu "= 0" kısmı çok önemli!
    val title: String,
    val content: String
)