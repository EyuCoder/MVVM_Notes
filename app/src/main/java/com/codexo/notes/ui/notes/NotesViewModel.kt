package com.codexo.notes.ui.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codexo.notes.data.Note
import com.codexo.notes.data.NoteDao
import com.codexo.notes.data.PrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val prefs: PrefsManager,
    private val noteDao: NoteDao
) : ViewModel() {

    private val TAG = NotesViewModel::class.java.simpleName

    lateinit var searchQuery: String

    val allNotes: LiveData<List<Note>>
        get() = getNotes()

    private fun getNotes(): LiveData<List<Note>> {
        Log.d(TAG, "getNotes: ${prefs.sortBy()}")
        return if (prefs.favoritePinnedStatus()) {
            noteDao.getNotesFavePinned(prefs.sortBy().toString())
        } else {
            noteDao.getNotes(prefs.sortBy().toString())
        }
    }

    fun searchNote(searchQuery: String): LiveData<List<Note>> = noteDao.searchNote(searchQuery)

    fun deleteAllNotes() = viewModelScope.launch { noteDao.clearNotes() }

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