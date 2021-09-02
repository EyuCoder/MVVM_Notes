package com.codexo.notes.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codexo.notes.data.Note
import com.codexo.notes.data.NoteDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val noteDao: NoteDao) : ViewModel() {

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { noteDao.insert(note) }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) { noteDao.update(note) }

    fun deleteItem(note: Note) = viewModelScope.launch(Dispatchers.IO) { noteDao.delete(note) }

    fun markAsFavorite(fave: Boolean, id: Long) =
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.markAsFavorite(
                fave,
                System.currentTimeMillis(),
                id
            )
        }
}