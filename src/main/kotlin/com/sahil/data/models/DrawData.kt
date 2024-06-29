package com.sahil.data.models

import com.sahil.utils.Constants.TYPE_DRAW_DATA

data class DrawData(
    val roomName:String,
    val color:String,
    val thickness:Float,
    val fromX : Float,
    val fromY : Float,
    val toX : Float,
    val toY : Float,
    val motionEvent : Int
):BaseModel(TYPE_DRAW_DATA)
