package com.sahil.data.models

import com.sahil.utils.Constants.TYPE_GAME_STATE

data class GameState(
    val drawingPlayer:String,
    val word: String
):BaseModel(TYPE_GAME_STATE)
