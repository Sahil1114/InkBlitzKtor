package com.sahil.data.models

import com.sahil.data.Room
import com.sahil.utils.Constants.TYPE_PHASE_CHANGE

data class PhaseChange(
    var phase: Room.Phase?,
    var time: Long,
    val drawingPlayer : String?= null
):BaseModel(TYPE_PHASE_CHANGE)
