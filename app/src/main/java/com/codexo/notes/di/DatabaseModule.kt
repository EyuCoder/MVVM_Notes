package com.codexo.notes.di

import android.content.Context
import androidx.room.Room
import com.codexo.notes.data.NoteDao
import com.codexo.notes.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): NoteDatabase {
        return Room.databaseBuilder(
                applicationContext,
                NoteDatabase::class.java,
                "notes_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao = noteDatabase.noteDao()
}