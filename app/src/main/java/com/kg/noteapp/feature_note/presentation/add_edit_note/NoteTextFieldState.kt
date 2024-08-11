package com.kg.noteapp.feature_note.presentation.add_edit_note

// bu şey için hinti görüyon mu görmüyon mu ondan
data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
