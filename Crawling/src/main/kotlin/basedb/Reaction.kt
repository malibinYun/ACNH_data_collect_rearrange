package basedb

import java.io.File
import java.lang.StringBuilder

fun main() {
    bindReactionData()
}

fun bindReactionData() {
    val nameEngKorMap = getNameEngKorMap()
    val nameEngImageUrlMap = getNameEngImageUrlMap()
    val stringBuilder = StringBuilder()
    File("raw", "reactionCsv.csv").forEachLine {
        val cursor = it.replace("\uFEFF", "").split(",")
        val nameEng = cursor[0]
        val nameKor = nameEngKorMap[nameEng]
        val source = cursor[2]
        val imageUrl = nameEngImageUrlMap[nameEng]
        stringBuilder
            .append(nameEng).append(",")
            .append(nameKor).append(",")
            .append(source).append(",")
            .append(imageUrl).append("\n")
    }
    println(stringBuilder.toString())

    val outputFile = File("fixed_data", "reaction_complete.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun getNameEngKorMap(): Map<String, String> {
    val map = HashMap<String, String>()
    File("raw", "reactionNameKorCsv.csv").forEachLine {
        val cursor = it.replace("\uFEFF", "").split(",")
        map[cursor[0]] = cursor[1]
    }
    return map
}

fun getNameEngImageUrlMap(): Map<String, String> {
    val map = HashMap<String, String>()
    File("raw", "reaction_urls.txt").forEachLine {
        val cursor = it.split(",")
        map[cursor[0]] = cursor[1]
    }
    return map
}