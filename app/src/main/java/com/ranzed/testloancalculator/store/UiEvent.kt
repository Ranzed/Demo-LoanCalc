package com.ranzed.testloancalculator.store

sealed interface UiEvent {
    data class ShowSuccess(val message: String) : UiEvent
    data class ShowError(val message: String) : UiEvent
}
