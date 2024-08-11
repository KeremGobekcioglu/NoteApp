package com.kg.noteapp.feature_note.domain.use_case
import com.kg.noteapp.feature_note.domain.model.Note
import com.kg.noteapp.feature_note.domain.repository.NoteRepository
import com.kg.noteapp.feature_note.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow
import com.kg.noteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.map

/*
We have used our interface as a parameter because we may want to test it without
real data. If we took RepoImpl as a parameter , we would have been testing with the real data.
 */

class GetNotes (
    private val repository : NoteRepository
)
{
    //Default is Date Descending
    operator fun invoke(
        noteOrder : NoteOrder = NoteOrder.Date(OrderType.Descending)
    )
    : Flow<List<Note>>
    {
        return repository.getNotes().map {
            notes ->
            when(noteOrder.orderType)
            {
                is OrderType.Ascending ->
                {
                    when(noteOrder)
                    {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending ->
                {
                    when(noteOrder)
                    {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}