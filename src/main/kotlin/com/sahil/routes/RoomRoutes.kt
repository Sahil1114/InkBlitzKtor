package com.sahil.routes

import com.sahil.data.Room
import com.sahil.data.models.BasicApiResponse
import com.sahil.data.models.CreateRoomRequest
import com.sahil.data.models.RoomResponse
import com.sahil.server
import com.sahil.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createRoomRoute(){
    route("/api/createRoom"){
        post {
            val roomRequest = call.receiveNullable<CreateRoomRequest>()
            if (roomRequest == null){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            if (server.rooms[roomRequest.name] !=null){
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(false,"Room already exist.")
                )
                return@post
            }
            if (roomRequest.maxPlayers<2){
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(false, "The minimum room size is 2")
                )
            }
            if (roomRequest.maxPlayers>Constants.MAX_ROOM_SIZE){
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(false, "The maximum room size is ${Constants.MAX_ROOM_SIZE}")
                )
            }

            val room= Room(
                roomRequest.name,
                roomRequest.maxPlayers,
            )

            server.rooms[roomRequest.name] =room
            println("Room created: ${roomRequest.name}")

            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse(true)
            )

        }
    }
}

fun Route.getRoomsRoute(){
    route("/api/getRooms"){
        get{
            val searchQuery = call.parameters["searchQuery"]
            if(searchQuery==null){
                call.respond(
                    HttpStatusCode.BadRequest
                )
                return@get
            }

            val roomResult= server.rooms.filterKeys {
                it.contains(searchQuery,ignoreCase = true)
            }

            val roomResponse = roomResult.values.map {
                RoomResponse(it.name,it.maxPlayers,it.players.size)
            }.sortedBy { it.name }

            call.respond(
                HttpStatusCode.OK,
                roomResponse
            )

        }
    }
}

fun Route.joinRoomRoute(){
    route("/api/joinRoom"){
        get {
            val userName= call.parameters["username"]
            val roomName= call.parameters["roomName"]

            if (userName == null|| roomName==null){
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val room = server.rooms[roomName]
            when{
                room== null ->{
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(false,"Room not found")
                    )
                }

                room.ifContainPlayer(userName) ->{
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(false,"A player with username already joined")
                    )
                }

                room.players.size >= room.maxPlayers ->{
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(false,"This room is already filled")
                    )
                }
                else ->{
                    call.respond(
                        HttpStatusCode.OK,
                        BasicApiResponse(true )
                    )
                }
            }
        }
    }
}