package com.kg.noteapp.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType) {
    data class Title(val ascOrdes: OrderType) : NoteOrder(ascOrdes)
    data class Color(val ascOrdes: OrderType) : NoteOrder(ascOrdes)
    data class Date(val ascOrdes: OrderType) : NoteOrder(ascOrdes)
}