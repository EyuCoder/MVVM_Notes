package com.codexo.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codexo.notes.utils.SortBy
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    fun getNotes(sortBy: String): LiveData<List<Note>> {
        return when (sortBy) {
            SortBy.TITLE.colName -> notesSortByTitle()
            SortBy.CREATED_AT.colName -> notesSortByCreatedAt()
            else -> notesSortByLastUpdated()
        }
    }

    fun getNotesFavePinned(sortBy: String): LiveData<List<Note>> {
        return when (sortBy) {
            SortBy.TITLE.colName -> notesSortByTitleFavePinned()
            SortBy.CREATED_AT.colName -> notesSortByCreatedAtFavePinned()
            else -> notesSortByLastUpdatedFavePinned()
        }
    }


    @Query("SELECT * FROM note_table ORDER BY title ASC")
    fun notesSortByTitle(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY last_updated_at DESC")
    fun notesSortByLastUpdated(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY created_at ASC")
    fun notesSortByCreatedAt(): LiveData<List<Note>>


    @Query("SELECT * FROM note_table ORDER BY favorite DESC, title ASC")
    fun notesSortByTitleFavePinned(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY favorite DESC, last_updated_at DESC")
    fun notesSortByLastUpdatedFavePinned(): LiveData<List<Note>>

    @Query("SELECT * FROM note_table ORDER BY favorite DESC, created_at ASC")
    fun notesSortByCreatedAtFavePinned(): LiveData<List<Note>>


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

    @Query("UPDATE note_table SET favorite = :fave, last_updated_at = :time WHERE id = :id")
    suspend fun markAsFavorite(fave: Boolean, time: Long, id: Long)

}