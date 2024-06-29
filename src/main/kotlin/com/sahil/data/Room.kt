package com.sahil.data

import com.sahil.data.models.Announcement
import com.sahil.gson
import io.ktor.websocket.*
import kotlinx.coroutines.isActive

class Room(
    val name:String,
    val maxPlayers: Int,
    var players :List<Player> = emptyList()
) {

    private var phaseChangedListener : ((Phase) -> Unit) ? = null

    var phase = Phase.WAITING_FOR_PLAYERS
        set(value) {
            synchronized(field){
                field=value
                phaseChangedListener?.let {change->
                    change(value)
                }
            }
        }

    private fun setPhaseChangeListener(listener:(Phase)-> Unit){
        phaseChangedListener=listener
    }

    init {
        setPhaseChangeListener {
            when(it){
                Phase.WAITING_FOR_PLAYERS -> waitingForPlayer()
                Phase.WAITING_FOR_START -> waitingForStart()
                Phase.NEW_ROUND -> newRound()
                Phase.GAME_RUNNING -> gameRunning()
                Phase.SHOW_WORD -> showWord()
            }
        }
    }

    suspend fun addPlayer(clientId: String,username: String,socketSession: WebSocketSession):Player{
        val player= Player(username,socketSession,clientId)
        players = players+player
        if (players.size==1){
            phase = Phase.WAITING_FOR_PLAYERS
        }
        else if(players.size==2 && phase==Phase.WAITING_FOR_PLAYERS){
            phase = Phase.WAITING_FOR_START
            players=players.shuffled()
        }
        else if(players.size== maxPlayers &&phase == Phase.WAITING_FOR_START ){
            phase= Phase.NEW_ROUND
            players=players.shuffled()
        }
        val announcement=Announcement(
            "$username joined the party!!",
            System.currentTimeMillis(),
            Announcement.TYPE_PLAYER_JOINED
        )
        broadcast(gson.toJson(announcement))
        return  player
    }

    suspend fun broadcast(message:String){
        players.forEach {player ->
            if (player.socket.isActive){
                player.socket.send(Frame.Text(message))
            }
        }
    }

    suspend fun broadcastToAllExcept(message:String,clientId:String){
        players.forEach {player ->
            if (player.clientId!=clientId && player.socket.isActive ){
                player.socket.send(Frame.Text(message))
            }
        }
    }

    fun ifContainPlayer(username:String):Boolean{
        return players.find {
            it.username==username }!=null
    }

    private fun waitingForPlayer(){}

    private fun waitingForStart(){}

    private fun newRound(){}

    private fun gameRunning(){}

    private fun showWord(){}



    enum class Phase{
        WAITING_FOR_PLAYERS,
        WAITING_FOR_START,
        NEW_ROUND,
        GAME_RUNNING,
        SHOW_WORD
    }
}