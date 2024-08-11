package com.kg.noteapp.di

import android.app.Application
import androidx.room.Room
import com.kg.noteapp.feature_note.data.data_source.NoteDatabase
import com.kg.noteapp.feature_note.data.repository.NoteRepositoryImpl
import com.kg.noteapp.feature_note.domain.repository.NoteRepository
import com.kg.noteapp.feature_note.domain.use_case.AddNote
import com.kg.noteapp.feature_note.domain.use_case.DeleteNote
import com.kg.noteapp.feature_note.domain.use_case.GetNoteById
import com.kg.noteapp.feature_note.domain.use_case.GetNotes
import com.kg.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/*Summary of What’s Happening:
Room Database Setup: Your NoteDatabase class has an abstract property noteDao of type NoteDao.
Room generates the implementation for this DAO, which provides methods to interact with the Note table in the database.

Repository Implementation: The NoteRepositoryImpl class takes this NoteDao
as a parameter and implements the NoteRepository interface by delegating the actual data operations to NoteDao.

Dependency Injection with Hilt: In the AppModule, the provideRepository
 method is responsible for creating an instance of NoteRepositoryImpl.
 Hilt will call this method when it needs to inject NoteRepository somewhere in your app.
 It passes the NoteDatabase instance to the method, from which the noteDao is extracted
 and passed to the NoteRepositoryImpl constructor.

In essence, Hilt is setting up and providing the dependencies (like NoteRepositoryImpl)
so that you don't have to manually create these objects yourself.
This allows for better separation of concerns, easier testing, and more maintainable code. */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app : Application) : NoteDatabase
    {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db : NoteDatabase) : NoteRepository
    {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository) : NoteUseCases
    {
        return NoteUseCases(getNotes = GetNotes(noteRepository) ,
            deleteNote = DeleteNote(noteRepository),
            addNote = AddNote(noteRepository),
            getNote = GetNoteById(noteRepository)
        )
    }
}