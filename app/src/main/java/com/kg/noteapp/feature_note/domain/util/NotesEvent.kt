package com.kg.noteapp.feature_note.domain.util

import com.kg.noteapp.feature_note.domain.model.Note

/**
 * Type Safety: The sealed class ensures that all possible event types
 * are known at compile time, reducing the risk of runtime errors.
 * Pattern Matching: You can use Kotlin's when expression to handle
 * each event type explicitly, making your code more readable and maintainable.
 * Encapsulation: Each event type can carry its own data,
 * encapsulating the information needed to handle that event.
 */
sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note : Note) : NotesEvent()
    data object RestoreNote: NotesEvent()
    data object ToggleOrderSection: NotesEvent()
}