package com.codexo.notes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.codexo.notes.data.Note
import com.codexo.notes.data.NoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel constructor(application: Application) : AndroidViewModel(application) {
    private val noteDao = NoteDatabase.getInstance(application).noteDao()

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { noteDao.insert(note) }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { noteDao.update(note) }

    fun deleteItem(note: Note) = viewModelScope.launch(Dispatchers.IO) { noteDao.delete(note) }

    fun markAsFavorite(fave: Boolean, id: Long) =
        viewModelScope.launch(Dispatchers.IO) { noteDao.markAsFavorite(fave, id) }
}