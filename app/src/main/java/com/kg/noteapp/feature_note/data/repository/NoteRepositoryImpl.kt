package com.kg.noteapp.feature_note.data.repository

import com.kg.noteapp.feature_note.data.data_source.NoteDao
import com.kg.noteapp.feature_note.domain.model.Note
import com.kg.noteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

/*
Dao property is used to access the database
Normally there are business logic here but it is a simple app
It looks like it is boilerplate code but not.
 */
class NoteRepositoryImpl(
    private val dao : NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}