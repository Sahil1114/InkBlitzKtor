package com.sahil

import com.sahil.data.Player
import com.sahil.data.Room
import java.util.concurrent.ConcurrentHashMap

class DrawingServer {
    val rooms = ConcurrentHashMap<String, Room>()
    val players = ConcurrentHashMap<String, Player>()

    fun playerJoined(player: Player){
        players[player.clientId] =player
    }

    fun getRoomWithClientId(clientId :String): Room?{
        val filterRooms=  rooms.filterValues {room ->
            room.players.find { player ->
                player.clientId== clientId
            }!=null
        }
        return if(filterRooms.isNotEmpty()) filterRooms.values.toList()[0] else null
    }
}