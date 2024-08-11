package com.kg.noteapp.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kg.noteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

/*DO NOT IMPLEMENT ANY LOGIC JUST GET THE DATA
  Logic will be implemented in RepositoryImpl if it is needed
 */
@Dao
interface NoteDao {


    @Query("SELECT * FROM note")
    fun getNotes() : Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int) : Note?

    /* If a note exist , replace it.
    So one stone , two bird. We do not need additional edit note function.*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNote(note: Note)
}