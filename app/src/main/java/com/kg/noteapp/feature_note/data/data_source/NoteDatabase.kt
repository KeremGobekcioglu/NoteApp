package com.kg.noteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kg.noteapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class], // Tables in the database
    version = 1
)
abstract class NoteDatabase : RoomDatabase(){
    // A DAO property to access DAO methods for performing database operations
    abstract val noteDao : NoteDao

    companion object
    {
        const val DB_NAME = "NOTES_DB"
    }
}