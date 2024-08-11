package com.kg.noteapp.feature_note.domain.repository

import com.kg.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow
/*
    We have this repo for easy testing
    and separating our data access from rest of the app.
 */
interface NoteRepository {

    fun getNotes() : Flow<List<Note>>

    suspend fun getNoteById(id : Int) : Note?

    suspend fun insertNote(note : Note)

    suspend fun deleteNote(note: Note)
}