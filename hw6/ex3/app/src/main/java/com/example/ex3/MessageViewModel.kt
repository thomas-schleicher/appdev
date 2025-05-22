package com.example.ex3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessageViewModel(): ViewModel() {
    private val repository = MessageRepository()
    private val _message = MutableStateFlow("Waiting for messages...")
    val message: StateFlow<String> = _message

    init {
        viewModelScope.launch {
            repository.getMessageFlow.collect {
                _message.value = it
            }
        }
    }
}