package com.sahil

import com.google.gson.Gson
import com.sahil.routes.createRoomRoute
import com.sahil.routes.gameWebSocketRoute
import com.sahil.routes.getRoomsRoute
import com.sahil.routes.joinRoomRoute
import com.sahil.session.DrawingSession
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.util.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

val server = DrawingServer()
val gson = Gson()

fun Application.module(testing:Boolean=false) {

    install(Sessions){
        cookie<DrawingSession>("SESSION")

    }

    intercept(ApplicationCallPipeline.Plugins){
        if(call.sessions.get<DrawingSession>()==null){
            val clientId= call.parameters["client_id"] ?: ""
            call.sessions.set(clientId, generateNonce())

        }
    }
    install(WebSockets)
    install(Routing){
        createRoomRoute()
        getRoomsRoute()
        joinRoomRoute()
        gameWebSocketRoute()
    }


    install(ContentNegotiation){
        gson {

        }
    }
    install(CallLogging)
}