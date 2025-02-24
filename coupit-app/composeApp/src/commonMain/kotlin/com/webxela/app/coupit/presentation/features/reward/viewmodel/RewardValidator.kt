package com.webxela.app.coupit.presentation.features.reward.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RewardValidator : ViewModel() {

    private val _state = MutableStateFlow(ValidatorState())
    val state = _state.asStateFlow()

    fun onEvent(event: ValidatorEvent) {
        when (event) {
            is ValidatorEvent.ValidateTitle -> validateTitle(event.title)
            is ValidatorEvent.ValidateDescription -> validateDescription(event.description)
            is ValidatorEvent.ValidateProbability -> validateProbability(event.probability)
            is ValidatorEvent.ValidateValidityHours -> validateValidityHours(event.hours)
            is ValidatorEvent.ValidateDiscountCode -> validateDiscountCode(event.code)
            ValidatorEvent.ClearErrors -> clearErrors()
        }
    }

    private fun validateTitle(title: String) {
        if (title.length > 25) {
            _state.update { it.copy(titleError = "Title cannot exceed 25 characters") }
        } else if (title.isBlank()) {
            _state.update { it.copy(titleError = "Title cannot be empty") }
        } else {
            _state.update { it.copy(titleError = null) }
        }
    }

    private fun validateDescription(description: String) {
        if (description.isBlank()) {
            _state.update { it.copy(descriptionError = "Description cannot be empty") }
        } else {
            _state.update { it.copy(descriptionError = null) }
        }
    }

    private fun validateProbability(probability: String) {
        val probabilityValue = probability.toFloatOrNull()
        when {
            probability.isBlank() -> _state.update {
                it.copy(probabilityError = "Probability cannot be empty")
            }
            probabilityValue == null -> _state.update {
                it.copy(probabilityError = "Invalid probability value")
            }
            probabilityValue !in 0f..100f -> _state.update {
                it.copy(probabilityError = "Probability must be between 0 and 100")
            }
            else -> _state.update { it.copy(probabilityError = null) }
        }
    }

    private fun validateValidityHours(hours: String) {
        val hoursValue = hours.toIntOrNull()
        when {
            hours.isBlank() -> _state.update {
                it.copy(validityHoursError = "Validity hours cannot be empty")
            }
            hoursValue == null -> _state.update {
                it.copy(validityHoursError = "Invalid hours value")
            }
            hoursValue !in 0..720 -> _state.update {
                it.copy(validityHoursError = "Hours must be between 0 and 720")
            }
            else -> _state.update { it.copy(validityHoursError = null) }
        }
    }

    private fun validateDiscountCode(code: String) {
        if (code.isBlank()) {
            _state.update { it.copy(discountCodeError = "Discount code cannot be empty") }
        } else {
            _state.update { it.copy(discountCodeError = null) }
        }
    }

    private fun clearErrors() {
        _state.update {
            it.copy(
                titleError = null,
                descriptionError = null,
                probabilityError = null,
                validityHoursError = null,
                discountCodeError = null
            )
        }
    }
}