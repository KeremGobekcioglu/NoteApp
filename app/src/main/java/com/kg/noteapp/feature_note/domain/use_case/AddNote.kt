package com.kg.noteapp.feature_note.domain.use_case

import com.kg.noteapp.feature_note.domain.model.InvalidNoteException
import com.kg.noteapp.feature_note.domain.model.Note
import com.kg.noteapp.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note)
    {
        if(note.title.isBlank())
        {
            throw InvalidNoteException("Title can not be empty")
        }
        if(note.content.isBlank())
        {
            throw InvalidNoteException("Are you sure that you do not want to write anything dude ?")
        }
        repository.insertNote(note)
    }
}