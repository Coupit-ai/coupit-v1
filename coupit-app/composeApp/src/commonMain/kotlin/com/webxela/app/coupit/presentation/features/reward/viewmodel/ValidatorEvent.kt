package com.webxela.app.coupit.presentation.features.reward.viewmodel

sealed interface ValidatorEvent {
    data class ValidateTitle(val title: String) : ValidatorEvent
    data class ValidateDescription(val description: String) : ValidatorEvent
    data class ValidateProbability(val probability: String) : ValidatorEvent
    data class ValidateValidityHours(val hours: String) : ValidatorEvent
    data class ValidateDiscountCode(val code: String) : ValidatorEvent
    data object ClearErrors : ValidatorEvent
}