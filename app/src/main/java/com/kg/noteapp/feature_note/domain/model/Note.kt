package com.kg.noteapp.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kg.noteapp.ui.theme.BabyBlue
import com.kg.noteapp.ui.theme.DarkGray
import com.kg.noteapp.ui.theme.LightBlue
import com.kg.noteapp.ui.theme.LightGreen
import com.kg.noteapp.ui.theme.RedOrange
import com.kg.noteapp.ui.theme.RedPink
import com.kg.noteapp.ui.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message : String ) : Exception(message)
