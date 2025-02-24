package com.webxela.app.coupit.presentation.features.reward.viewmodel

data class ValidatorState(
    val titleError: String? = null,
    val descriptionError: String? = null,
    val probabilityError: String? = null,
    val validityHoursError: String? = null,
    val discountCodeError: String? = null
)