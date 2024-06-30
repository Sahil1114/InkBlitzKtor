package com.sahil.data.models

import com.sahil.utils.Constants.TYPE_CHOSEN_WORD

data class ChosenWord (
    val chosenWord: String,
    val roomName:String
):BaseModel(TYPE_CHOSEN_WORD)