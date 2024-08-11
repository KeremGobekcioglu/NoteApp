package com.kg.noteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kg.noteapp.feature_note.domain.util.NoteOrder
import com.kg.noteapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier : Modifier = Modifier,
    noteOrder : NoteOrder = NoteOrder.Date(OrderType.Descending),
    // A function that takes a NoteOrder as a parameter and returns Unit(void)
    onOrderChange : (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            MyRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelection = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            MyRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelection = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            MyRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelection = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            MyRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelection = {
                    onOrderChange(
                        when (noteOrder) {
                            // Ascending or Descending
                            is NoteOrder.Title -> noteOrder.copy(ascOrdes = OrderType.Ascending)
                            is NoteOrder.Color -> noteOrder.copy(ascOrdes = OrderType.Ascending)
                            is NoteOrder.Date -> noteOrder.copy(ascOrdes = OrderType.Ascending)
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.width(8.dp))

            MyRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelection = {
                    onOrderChange(
                        when (noteOrder) {
                            // Ascending or Descending
                            is NoteOrder.Title -> noteOrder.copy(ascOrdes = OrderType.Descending)
                            is NoteOrder.Color -> noteOrder.copy(ascOrdes = OrderType.Descending)
                            is NoteOrder.Date -> noteOrder.copy(ascOrdes = OrderType.Descending)
                        }
                    )
                }
            )
        }
    }
}