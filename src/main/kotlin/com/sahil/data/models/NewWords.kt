package com.sahil.data.models

import com.sahil.utils.Constants.TYPE_NEW_WORD

data class NewWords(
    val newWords: List<String>
):BaseModel(TYPE_NEW_WORD)
