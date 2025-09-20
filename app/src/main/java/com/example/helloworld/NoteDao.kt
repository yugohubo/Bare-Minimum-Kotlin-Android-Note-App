package com.example.helloworld

import androidx.lifecycle.LiveData // Bu import'u eklediğinden emin ol
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note): Long

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>> // <-- ÇÖZÜM: Geri dönüş tipini eklemek.
}