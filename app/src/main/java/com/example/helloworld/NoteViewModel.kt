package com.example.helloworld

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Artık 'AndroidViewModel' kullanıyoruz çünkü veritabanını başlatmak için 'application context'e ihtiyacımız var.
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    // Veritabanına erişim için DAO referansı
    private val noteDao: NoteDao
    // Gözetlenecek olan not listesi (LiveData)
    val notes: LiveData<List<Note>>

    init {
        // ViewModel ilk oluşturulduğunda veritabanı örneğini ve DAO'yu al
        val database = NoteDatabase.getDatabase(application)
        noteDao = database.noteDao()
        // DAO üzerinden tüm notları LiveData olarak al
        notes = noteDao.getAllNotes()
    }

    /**
     * Yeni bir notu veritabanına eklemek için kullanılan fonksiyon.
     * Bu işlem arka planda (IO Dispatcher) bir coroutine içinde yapılır.
     */
    fun addNote(title: String, content: String) {
        // viewModelScope, ViewModel'in yaşam döngüsüne bağlı bir coroutine scope'udur.
        // ViewModel yok olduğunda bu scope'taki tüm işler iptal edilir.
        viewModelScope.launch(Dispatchers.IO) {
            val newNote = Note(title = title, content = content)
            noteDao.insert(newNote)
        }
    }
}