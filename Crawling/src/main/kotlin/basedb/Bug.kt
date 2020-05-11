package basedb

import joinListWithComma
import java.io.File
import java.lang.StringBuilder

fun main() {
    bindBugNameAndUrl()
}

fun bindBugNameAndUrl() {
    val bugIconMap = getBugIconMap()
    val bugDetailMap = getBugDetailMap()
    val bugIndexNameKorMap = getBugIndexNameKorMap()
    val csvTexts = File("raw", "bugCsv.csv").useLines { it.toList() }
    val stringBuilder = StringBuilder()

    for (i in 1 until csvTexts.size) {
        val cursor = csvTexts[i].split(",")
        val index = cursor[0].toInt()
        stringBuilder
            .append(index).append(",")
            .append(bugIndexNameKorMap[index]).append(",")
            .append(joinListWithComma(cursor.subList(1, cursor.size))).append(",")
            .append(bugIconMap[index]).append(",")
            .append(bugDetailMap[index]).append("\n")
    }
    println(stringBuilder.toString())

    val outputFile = File("fixed_data", "bug_complete.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun getBugIndexNameKorMap(): Map<Int, String> {
    val map = HashMap<Int, String>()
    File("raw", "bug_index_nameKor.txt").forEachLine {
        val cursor = it.split("\t")
        map[cursor[0].toInt()] = cursor[2]
    }
    return map
}

fun getBugIconMap(): Map<Int, String> {
    val map = HashMap<Int, String>()
    File("raw", "bug_icon_urls.txt").forEachLine {
        val cursor = it.split(",")
        map[cursor[0].toInt()] = cursor[1]
    }
    return map
}

fun getBugDetailMap(): Map<Int, String> {
    val map = HashMap<Int, String>()
    File("raw", "bug_detail_urls.txt").forEachLine {
        val cursor = it.split(",")
        map[cursor[0].toInt()] = cursor[1]
    }
    return map
}