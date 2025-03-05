package com.webxela.app.coupit.presentation.features.reward.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.minimumInteractiveComponentSize
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import com.webxela.app.coupit.domain.model.Reward
import com.webxela.app.coupit.presentation.features.reward.viewmodel.ValidatorEvent
import com.webxela.app.coupit.presentation.features.reward.viewmodel.ValidatorState


@Composable
fun RewardDialog(
    modifier: Modifier = Modifier,
    reward: Reward? = null,
    validatorState: ValidatorState,
    onDismiss: () -> Unit,
    onSaveClicked: (reward: Reward) -> Unit,
    onUpdateClicked: (rewardId: String?, reward: Reward) -> Unit,
    validatorEvent: (ValidatorEvent) -> Unit
) {
    var title by remember { mutableStateOf(reward?.title.orEmpty()) }
    var description by remember { mutableStateOf(reward?.description.orEmpty()) }
    var probability by remember {
        mutableStateOf((reward?.probability?.times(100))?.toString().orEmpty())
    }
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
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        validatorEvent(ValidatorEvent.ValidateTitle(it))
                    },
                    label = { Text("Title") },
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 1,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium,
                    isError = validatorState.titleError != null,
                    supportingText = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            validatorState.titleError?.let { Text(it) }
                            Text("${title.length}/25", textAlign = TextAlign.Right)
                        }
                    }
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        validatorEvent(ValidatorEvent.ValidateDescription(it))
                    },
                    label = { Text("Description") },
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 3,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium,
                    isError = validatorState.descriptionError != null,
                    supportingText = { validatorState.descriptionError?.let { Text(it) } }
                )

                OutlinedTextField(
                    value = probability,
                    onValueChange = {
                        if (it.isEmpty() || (it.toFloatOrNull() != null)) {
                            probability = it
                            validatorEvent(ValidatorEvent.ValidateProbability(it))
                        }
                    },
                    label = { Text("Probability (1-100%)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 1,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium,
                    isError = validatorState.probabilityError != null,
                    supportingText = { validatorState.probabilityError?.let { Text(it) } }
                )

                OutlinedTextField(
                    value = validityHours,
                    onValueChange = {
                        if (it.isEmpty() || it.toIntOrNull() != null) {
                            validityHours = it
                            validatorEvent(ValidatorEvent.ValidateValidityHours(it))
                        }
                    },
                    label = { Text("Validity (hours)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 1,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium,
                    isError = validatorState.validityHoursError != null,
                    supportingText = { validatorState.validityHoursError?.let { Text(it) } }
                )

                OutlinedTextField(
                    value = discountCode,
                    onValueChange = {
                        discountCode = it
                        validatorEvent(ValidatorEvent.ValidateDiscountCode(it))
                    },
                    label = { Text("Discount Code") },
                    modifier = textFieldModifier,
                    enabled = textFieldEnabled,
                    colors = textFieldColors,
                    maxLines = 1,
                    textStyle = commonTextFieldStyle,
                    shape = MaterialTheme.shapes.medium,
                    isError = validatorState.discountCodeError != null,
                    supportingText = { validatorState.discountCodeError?.let { Text(it) } }
                )
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.animateContentSize()
            ) {
                Button(onClick = {
                    validatorEvent(ValidatorEvent.ClearErrors)
                    onDismiss()
                }) {
                    Text("Cancel")
                }
                if (!isEditMode || isEditing) {
                    Button(
                        onClick = {
                            val hasErrors = validatorState.run {
                                titleError != null || descriptionError != null ||
                                        probabilityError != null || validityHoursError != null ||
                                        discountCodeError != null
                            }
                            if (!hasErrors) {
                                val newReward = Reward(
                                    id = null,
                                    title = title,
                                    description = description,
                                    probability = probability.toDoubleOrNull()?.div(100) ?: 0.0,
                                    validityHours = validityHours.toIntOrNull() ?: 0,
                                    discountCode = discountCode,
                                    createdAt = reward?.createdAt,
                                )
                                if (isEditMode) {
                                    Logger.e("RewardDialog Reward: ${reward.toString()}")
                                    onUpdateClicked(reward?.id, newReward)
                                } else onSaveClicked(newReward)
                                onDismiss()
                            }
                        }
                    ) {
                        Text(if (isEditMode) "Update" else "Create")
                    }
                }
            }
        }
    )
}