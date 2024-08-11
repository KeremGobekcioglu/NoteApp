package com.kg.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kg.noteapp.feature_note.domain.model.InvalidNoteException
import com.kg.noteapp.feature_note.domain.model.Note
import com.kg.noteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _title = mutableStateOf(NoteTextFieldState(
        hint = "Enter title..."
    ))
    val title : State<NoteTextFieldState> = _title

    private val _content = mutableStateOf(NoteTextFieldState(
        hint = "Write your note here..."
    ))
    val content : State<NoteTextFieldState> = _content

    private val _color = mutableIntStateOf(Note.noteColors.random().toArgb())
    val color : State<Int> = _color

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId : Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let {
            noteId ->
            if(noteId != -1)
            {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also {
                        note -> currentNoteId = note.id
                        _title.value = title.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _content.value = content.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        // dikkat et
                        _color.intValue = note.color

                    }
                }
            }
        }
    }

    fun onEvent(event : AddEditNoteEvent)
    {
        when(event)
        {
            is AddEditNoteEvent.EnterTitle -> {
                _title.value = title.value.copy(
                    text = event.value
                )
            }
            // Text should be empty when hint is visible
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _title.value = title.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            title.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnterContent ->
            {
                _content.value = content.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus ->
            {
                _content.value = content.value.copy(
                    isHintVisible = !(event.focusState.isFocused) &&
                            content.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor ->
            {
                _color.intValue = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                // Emit SaveNote event
                viewModelScope.launch {
                    try{
                        noteUseCases.addNote(
                            Note(
                                title = title.value.text,
                                content = content.value.text,
                                color = color.value,
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }
                    catch (e : InvalidNoteException)
                    {
                        _eventFlow.emit(UiEvent.ShowSnackbar(
                            message = e.message ?: "Could not save the note"
                        ))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveNote : UiEvent()
    }
}