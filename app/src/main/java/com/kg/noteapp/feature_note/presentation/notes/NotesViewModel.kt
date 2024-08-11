package com.kg.noteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kg.noteapp.feature_note.domain.model.Note
import com.kg.noteapp.feature_note.domain.use_case.NoteUseCases
import com.kg.noteapp.feature_note.domain.util.NoteOrder
import com.kg.noteapp.feature_note.domain.util.NotesEvent
import com.kg.noteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel()
{

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state
    private var lastDeletedNote : Note? = null
    // To check if there are more than one job exists and works simultaneously
    private var getNotesJob : Job? = null

    init
    {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event : NotesEvent)
    {
        when(event)
        {
            // Last deleted note is keeped so that we can restore it if user wants it
            is NotesEvent.DeleteNote ->
            {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    lastDeletedNote = event.note
                }
            }
            is NotesEvent.Order ->
            {
                /*::class checks the type , if you dont use you check the references like java strings
                orderTypes are data classes so equal function is overriden you dont need
                 ::class for them , noteOrder is not data class so you need ::class */
                if(state.value.noteOrder::class == event.noteOrder::class

                    && state.value.noteOrder.orderType == event.noteOrder.orderType)
                {
                    return
                }
                getNotes(event.noteOrder)

            }
            is NotesEvent.RestoreNote ->
            {
                viewModelScope.launch {
                    /*
                    Both are same , the one in comment is more readable and easy to get
                    if (lastDeletedNote != null) {
                        noteUseCases.addNote(lastDeletedNote)
                        lastDeletedNote = null
                    } */
                    noteUseCases.addNote(lastDeletedNote ?: return@launch)
                    lastDeletedNote = null
                }
            }
            is NotesEvent.ToggleOrderSection ->
            {
                // There are 2 choices and a change happens so reverse it
                _state.value = state.value.copy(
                    isOrderSelectionVisible = !state.value.isOrderSelectionVisible)
            }
        }
    }

    /**
     * Reason for canceling any existing job before starting a new one in the getNotes
     * function is to ensure that there is only one active job fetching
     * notes at any given time. This prevents multiple concurrent jobs
     * from running simultaneously,
     * which could lead to inconsistent state updates or resource contention.
     */
    private fun getNotes(noteOrder: NoteOrder)
    {
        // Cancel any existing job for fetching notes
        getNotesJob?.cancel()
        // Start a new job to fetch notes using the provided noteOrder
        getNotesJob = noteUseCases.getNotes(noteOrder).onEach {
            newNotes ->
            _state.value = state.value.copy(notes  = newNotes,
                noteOrder = noteOrder)
        }.launchIn(viewModelScope)
    }
}