package com.sahil.data.models

import com.sahil.utils.Constants.TYPE_GAME_ERROR

data class GameError(
    val errorType:Int
):BaseModel(TYPE_GAME_ERROR){
    companion object{
        const val ERROR_ROOM_NOT_FOUND =0
    }
}
