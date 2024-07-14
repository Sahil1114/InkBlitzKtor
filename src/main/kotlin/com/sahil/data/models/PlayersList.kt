package com.sahil.data.models

import com.sahil.utils.Constants.TYPE_PLAYERS_LIST

data class PlayersList(
    val players:List<PlayerData>
):BaseModel(TYPE_PLAYERS_LIST)
