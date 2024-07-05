package com.sahil.utils

import com.sahil.data.models.ChatMessage

fun ChatMessage.matchesWord(word:String):Boolean{
    return message.toLowerCase().trim() == word.toLowerCase().trim()
}