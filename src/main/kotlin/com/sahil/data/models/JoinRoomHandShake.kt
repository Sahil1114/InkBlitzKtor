package com.sahil.data.models

import com.sahil.utils.Constants.TYPE_JOIN_ROOM_HANDSHAKE

data class JoinRoomHandShake (
    val userName:String,
    val roomName:String,
    val clientId:String
):BaseModel(TYPE_JOIN_ROOM_HANDSHAKE)