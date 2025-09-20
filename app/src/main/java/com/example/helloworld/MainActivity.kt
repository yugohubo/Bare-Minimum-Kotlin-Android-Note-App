package com.example.helloworld

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // 1. ViewModel'i oluşturma ve bağlama (Aşçı'yı işe alma)
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 2. Arayüz elemanlarını koda bağlama
        val editTextTitle = findViewById<EditText>(R.id.editTextTitle)
        val editTextContent = findViewById<EditText>(R.id.editTextContent)
        val buttonAddNote = findViewById<Button>(R.id.buttonAddNote)
        val textViewNotesList = findViewById<TextView>(R.id.textViewNotesList)

        // 3. ViewModel'den gelen veriyi "gözetleme" (Sihirli tepsiyi izleme)
        noteViewModel.notes.observe(this) { notes ->
            // LiveData'daki not listesi her değiştiğinde bu kod bloğu ÇALIŞACAK
            if (notes.isEmpty()) {
                textViewNotesList.text = getString(R.string.empty_note)
            } else {
                // Bütün notları formatlayıp tek bir metin haline getir
                val formattedNotes = notes.joinToString("\n\n") { note ->
                    "Başlık: ${note.title}\nİçerik: ${note.content}"
                }
                textViewNotesList.text = formattedNotes
            }
        }

        // 4. Butonun tıklanma olayını dinleme (Sipariş alma)
        buttonAddNote.setOnClickListener {
            val title = editTextTitle.text.toString()
            val content = editTextContent.text.toString()

            // Başlık ve içerik boş değilse...
            if (title.isNotBlank() && content.isNotBlank()) {
                // ...ViewModel'e "yeni not ekle" komutunu gönder (Siparişi Aşçı'ya ilet)
                noteViewModel.addNote(title, content)

                // Ekleme sonrası giriş alanlarını temizle (kullanıcı deneyimi için)
                editTextTitle.text.clear()
                editTextContent.text.clear()
            }
        }
    }
}