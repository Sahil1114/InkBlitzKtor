package com.sahil.utils

import java.io.File

val words = readWordList("resources/drawing_words.txt")
fun readWordList(fileName:String):List<String>{
    val inputStream = File(fileName).inputStream()
    val words = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { words.add(it) }
    return words
}

fun getRandomWords(amount:Int):List<String>{
    var currAmount=0
    val result= mutableListOf<String>()
    while (currAmount<amount){
        val word=words.random()
        if (!result.contains(word)){
            result.add(word)
        }
    }
    return result
}

fun String.transformToUnderscores()=
    toCharArray().map {
        if (it!=' ')'_' else ' '
    }.joinToString { " " }