package com.kg.noteapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyRadioButton(
    text : String,
    selected : Boolean,
    onSelection: () -> Unit,
    modifier : Modifier = Modifier
)
{
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        RadioButton(selected = selected,
            onClick = onSelection,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.onSecondary,
                unselectedColor = MaterialTheme.colors.primary
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text , style = MaterialTheme.typography.body1 ,
            color = MaterialTheme.colors.onSurface)
    }
}