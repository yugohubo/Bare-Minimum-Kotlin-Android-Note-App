package com.example.helloworld

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            // Eğer INSTANCE null değilse, direkt onu döndür.
            // Eğer null ise, synchronized bloğuna girerek thread-safe bir şekilde oluştur.
            // Bu "double-checked locking" yöntemi, aynı anda birden fazla veritabanı
            // nesnesi oluşturulmasını kesin olarak engeller.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    // Veritabanı oluşturulurken yapılacak ek işlemler (migration gibi) buraya eklenebilir.
                    .build()
                INSTANCE = instance
                // instance'ı geri döndür
                instance
            }
        }
    }
}