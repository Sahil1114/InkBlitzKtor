package com.sahil.routes

import com.google.gson.JsonParser
import com.sahil.data.models.BaseModel
import com.sahil.gson
import com.sahil.session.DrawingSession
import com.sahil.utils.Constants.TYPE_CHAT_MESSAGE
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.lang.Exception

fun Route.standardWebSocket(
    handleFrame:suspend (
        socket : DefaultWebSocketServerSession,
        clientId : String,
        message : String,
        payload : BaseModel
    ) -> Unit
){
    webSocket {
        val session =  call.sessions.get<DrawingSession>()

        if(session==null){
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY,"No session"))
            return@webSocket
        }

        try {
            incoming.consumeEach {frame->
                if(frame is Frame.Text){
                    val message= frame.readText()
                    val jsonObject = JsonParser.parseString(message).asJsonObject
                    val type = when(jsonObject.get("type").asString){
                        TYPE_CHAT_MESSAGE ->{
                            BaseModel::class.java
                        }
                        else-> BaseModel::class.java
                    }
                    val payload = gson.fromJson(message,type)
                    handleFrame(this,session.clientId ,message, payload)
                }
            }

        }catch (e : Exception){
            e.printStackTrace()
        }
        finally {
            // Handle disconnet
        }

    }
}