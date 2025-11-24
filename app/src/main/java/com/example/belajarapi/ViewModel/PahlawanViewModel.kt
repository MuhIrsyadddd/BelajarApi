package com.example.belajarapi.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.belajarapi.Data.PahlawanResponseItem
import com.example.belajarapi.Data.RetrofitClient
import kotlinx.coroutines.launch


sealed interface PahlawanUiState {
    object Loading : PahlawanUiState
    data class Success(val data:List<PahlawanResponseItem>) : PahlawanUiState
    object Error: PahlawanUiState
}

class PahlawanViewModel : ViewModel() {
    var pahlawanUiState: PahlawanUiState by mutableStateOf(PahlawanUiState.Loading)
    private  set

    init {
        getPahlawan()
    }

    fun getPahlawan(){
        viewModelScope.launch {
            pahlawanUiState = PahlawanUiState.Loading

            try {
                val result = RetrofitClient.instance.getPahlawan()

                pahlawanUiState = PahlawanUiState.Success(result)
            } catch (e: Exception){
                pahlawanUiState = PahlawanUiState.Error

                e.printStackTrace()
            }
        }
    }
}