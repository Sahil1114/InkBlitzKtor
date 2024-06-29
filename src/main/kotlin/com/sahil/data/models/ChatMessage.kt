package com.sahil.data.models

import com.sahil.utils.Constants.TYPE_CHAT_MESSAGE

data class ChatMessage (
    val from : String,
    val to : String,
    val message : String,
    val timeStamp : Long
):BaseModel(TYPE_CHAT_MESSAGE)