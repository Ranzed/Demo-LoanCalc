package com.ranzed.testloancalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ranzed.testloancalculator.store.LoanStore
import com.ranzed.testloancalculator.store.UiEvent
import com.ranzed.testloancalculator.ui.CalculatorScreen
import com.ranzed.testloancalculator.ui.theme.TestLoanCalculatorTheme

class MainActivity : ComponentActivity() {
    private val storeFactory by lazy {
        LoanStore.factory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val store: LoanStore = viewModel(factory = storeFactory)
            val state by store.state.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }

            LaunchedEffect(Unit) {
                store.events.collect { event ->
                    when (event) {
                        is UiEvent.ShowSuccess -> snackbarHostState.showSnackbar(event.message)
                        is UiEvent.ShowError -> snackbarHostState.showSnackbar(event.message)
                    }
                }
            }

            TestLoanCalculatorTheme {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { padding ->
                    CalculatorScreen(
                        state = state,
                        onAction = store::dispatch,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}