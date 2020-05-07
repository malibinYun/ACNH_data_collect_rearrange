package basedb

import joinListWithComma
import java.io.File
import java.lang.StringBuilder

fun main() {
    bindKorNameAndUrl()
}

fun bindKorNameAndUrl() {
    val fishNameMap = getFishIndexNameKorMap()
    val stringBuilder = StringBuilder()
    val fileTexts = File("raw", "fishCsv.csv").useLines { it.toList() }
    val fishIconMap = getFishIconMap()
    val fishDetailMap = getFishDetailMap()

    for (i in 1 until fileTexts.size) {
        val cursor = fileTexts[i].split(",")
        val index = cursor[0].toInt()
        val fishNameEng = cursor[1].replace("\\s".toRegex(), "")
        stringBuilder
            .append(index).append(",")
            .append(fishNameMap[index]).append(",")
            .append(joinListWithComma(cursor.subList(1, cursor.size - 1))).append(",")
            .append(fishIconMap[fishNameEng]).append(",")
            .append(fishDetailMap[fishNameEng]).append("\n")
    }
    println(stringBuilder.toString())

    val outputFile = File("fixed_data", "fish_complete.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun getFishIndexNameKorMap(): Map<Int, String> {
    val map = HashMap<Int, String>()
    File("raw", "fish_index_nameKor.txt").forEachLine {
        val cursor = it.split("\t")
        map[cursor[0].toInt()] = cursor[2]
    }
    return map
}

fun getFishIconMap(): Map<String, String> {
    val map = HashMap<String, String>()
    File("raw", "fish_icon_urls.txt").forEachLine {
        val cursor = it.split(",")
        map[cursor[0]] = cursor[1]
    }
    return map
}

fun getFishDetailMap(): Map<String, String> {
    val map = HashMap<String, String>()
    File("raw", "fish_detail_urls.txt").forEachLine {
        val cursor = it.split(",")
        map[cursor[0]] = cursor[1]
    }
    return map
}