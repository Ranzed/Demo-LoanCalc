package com.ranzed.testloancalculator.store

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.ranzed.testloancalculator.data.LoanApiRepositoryImpl
import com.ranzed.testloancalculator.data.LoanPrefsRepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

class LoanStore(
    private val middleware: LoanMiddleware
) : ViewModel() {
    private val _state = MutableStateFlow(LoanState())
    val state: StateFlow<LoanState> = _state.asStateFlow()

    private val _events = Channel<UiEvent>(Channel.BUFFERED)
    val events: Flow<UiEvent> = _events.receiveAsFlow()

    init {
        middleware.init(
            getState = { _state.value },
            dispatch = ::dispatch,
            sendEvent = { viewModelScope.launch { _events.send(it) } }
        )
        middleware.restoreState()
    }

    fun dispatch(action: LoanAction) {
        _state.update { loanReducer(it, action) }
        middleware.handleSideEffects(action, viewModelScope)
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val prefs = context.getSharedPreferences("loan_prefs", Context.MODE_PRIVATE)
                    val middleware = LoanMiddleware(
                        apiRepository = LoanApiRepositoryImpl(OkHttpClient()),
                        prefsRepository = LoanPrefsRepositoryImpl(prefs)
                    )
                    @Suppress("UNCHECKED_CAST")
                    return LoanStore(middleware) as T
                }
            }
    }
}
