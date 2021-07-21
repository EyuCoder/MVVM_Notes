package com.codexo.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    fun getNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE title LIKE '%' || :searchQuery || '%' ORDER BY id DESC")
    fun searchNote(searchQuery: String): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("DELETE FROM note_table")
    suspend fun clearNotes()

    @Query("SELECT * FROM note_table ORDER BY :sortBy DESC")
    fun sortBy(sortBy: String): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY favorite DESC")
    fun sortByFavoriteDesc(): LiveData<List<Note>>

    @Query("UPDATE note_table SET favorite = :fave WHERE id = :id")
    suspend fun markAsFavorite(fave: Boolean, id: Long)

}