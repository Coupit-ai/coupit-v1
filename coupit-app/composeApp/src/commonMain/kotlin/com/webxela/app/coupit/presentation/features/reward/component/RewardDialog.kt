package com.webxela.app.coupit.presentation.features.reward.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.webxela.app.coupit.domain.model.Reward


@Composable
fun RewardDialog(
    modifier: Modifier = Modifier,
    reward: Reward? = null,
    onDismiss: () -> Unit,
    onSaveClicked: (reward: Reward) -> Unit
) {
    var title by remember { mutableStateOf(reward?.title.orEmpty()) }
    var description by remember { mutableStateOf(reward?.description.orEmpty()) }
    var probability by remember { mutableStateOf((reward?.probability?.times(100))?.toString().orEmpty()) }
    var validityHours by remember { mutableStateOf(reward?.validityHours?.toString().orEmpty()) }
    var discountCode by remember { mutableStateOf(reward?.discountCode.orEmpty()) }
    var isEditing by remember { mutableStateOf(false) }

    val isEditMode = reward != null
    val dialogTitle = if (isEditMode) {
        if (isEditing) "Edit Reward" else "Reward"
    } else "Create New Reward"

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        disabledTextColor = MaterialTheme.colorScheme.onSurface,
        disabledBorderColor = Color.Transparent,
        disabledLabelColor = MaterialTheme.colorScheme.primary
    )

    val textFieldModifier = Modifier.fillMaxWidth()
    val textFieldEnabled = !isEditMode || isEditing
    val commonTextFieldStyle = MaterialTheme.typography.titleMedium

    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = textFieldModifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = dialogTitle, fontWeight = FontWeight.Medium)
                if (isEditMode) {
                    IconButton(onClick = { isEditing = !isEditing }) {
                        Icon(
                            imageVector = if (isEditing) Icons.Default.EditOff else Icons.Default.Edit,
                            contentDescription = if (isEditing) "Done Editing" else "Edit",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 1,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 3,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium
                )

                OutlinedTextField(
                    value = probability,
                    onValueChange = {
                        if (it.isEmpty() || (it.toFloatOrNull()?.let { it in 0f..100f } == true)) {
                            probability = it
                        }
                    },
                    label = { Text("Probability (1-100%)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 1,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium
                )

                OutlinedTextField(
                    value = validityHours,
                    onValueChange = {
                        if (it.isEmpty() || (it.toIntOrNull()?.let { it in 0..720 } == true)) {
                            validityHours = it
                        }
                    },
                    label = { Text("Validity (hours)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 1,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium
                )

                OutlinedTextField(
                    value = discountCode,
                    onValueChange = { discountCode = it },
                    label = { Text("Discount Code") },
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 1,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium
                )
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.animateContentSize()
            ) {
                Button(onClick = onDismiss) { Text("Cancel") }
                if (!isEditMode || isEditing) {
                    Button(
                        onClick = {
                            onSaveClicked(
                                Reward(
                                    id = reward?.id,
                                    title = title,
                                    description = description,
                                    probability = probability.toDoubleOrNull()?.div(100) ?: 0.0,
                                    validityHours = validityHours.toIntOrNull() ?: 0,
                                    discountCode = discountCode,
                                    createdAt = null
                                )
                            )
                            onDismiss()
                        }
                    ) {
                        Text(if (isEditMode) "Update" else "Create")
                    }
                }
            }
        }
    )
}