package com.kg.noteapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

/**
 * This class is used to represent the
 * events that can occur in the AddEditNote screen.
 */
sealed class AddEditNoteEvent{
    data class EnterTitle(val value: String): AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnterContent(val value: String): AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColor(val color: Int): AddEditNoteEvent()
    data object SaveNote: AddEditNoteEvent()
}
