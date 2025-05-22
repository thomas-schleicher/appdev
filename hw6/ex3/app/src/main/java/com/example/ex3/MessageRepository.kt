package com.example.ex3

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MessageRepository (){
    private val dataSource = MessageDataSource()
        val getMessageFlow: Flow<String> = flow {
        for (i in 0..100) {
            emit("Message $i: ${dataSource.messageData[i%dataSource.messageData.size]}")
            delay(2000)
        }
    }

}