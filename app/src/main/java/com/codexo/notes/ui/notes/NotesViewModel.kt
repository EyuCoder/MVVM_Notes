package com.codexo.notes.ui.notes

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.codexo.notes.data.Note
import com.codexo.notes.data.NoteDatabase
import com.codexo.notes.data.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel constructor(application: Application) : AndroidViewModel(application) {
    private val TAG = NotesViewModel::class.java.simpleName
    private val noteDao = NoteDatabase.getInstance(application).noteDao()

    lateinit var searchQuery: String

    private val prefs = PreferenceManager(application)

    val allNotes: LiveData<List<Note>>
        get() = getNotes()

    fun getNotes(): LiveData<List<Note>> {
        Log.d(TAG, "getNotes: ${prefs.sortBy()}")
        return if (prefs.favoritePinnedStatus()) {
            noteDao.getNotesFavePinned(prefs.sortBy().toString())
        } else {
            noteDao.getNotes(prefs.sortBy().toString())
        }
    }

    fun searchNote(searchQuery: String): LiveData<List<Note>> = noteDao.searchNote(searchQuery)

    fun deleteAllNotes() = viewModelScope.launch { noteDao.clearNotes() }

    fun markAsFavorite(fave: Boolean, id: Long) =
        viewModelScope.launch(Dispatchers.IO) { noteDao.markAsFavorite(fave, id) }

    fun initDummyNote() = viewModelScope.launch {
        noteDao.insert(
            Note(
                title = "hey",
                note = "this is A dummy note!",
                favorite = true,
                archived = false,
                lastUpdatedAt = System.currentTimeMillis(),
                bgColor = -1
            )
        )
    }
}