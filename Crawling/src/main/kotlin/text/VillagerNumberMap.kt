package text

import java.io.File
import java.lang.StringBuilder

// 아미보 카드 숫자와 주민 이름 매칭을 위한 데이터를 만들기위해 만들엇음 ㅠ

fun main() {
    writeVillager1to200()
    writeVillager201to400()
    writeVillager401to450()
}

fun writeVillager1to200() {
    val inputRawFile = File("D:\\modelmaker\\remoteCode\\Crawling\\raw\\villager_num_1_200_raw.txt")
    val stringBuilder = StringBuilder()
    inputRawFile.forEachLine {
        val cursor = it.split('\t')
        stringBuilder
            .append(cursor[AMIIBO_INDEX])
            .append(",")
            .append(cursor[VILLAGER_NAME].toLowerCase())
//            .append(",")
//            .append(cursor[text.SPECIES].toLowerCase())
            .append("\n")
    }

    val outputFile = File("D:\\modelmaker\\remoteCode\\Crawling\\outputText", "villagersNum1_200.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun writeVillager201to400() {
    val inputRawFile = File("D:\\modelmaker\\remoteCode\\Crawling\\raw\\villager_num_201_400_raw.txt")
    val stringBuilder = StringBuilder()
    inputRawFile.forEachLine {
        val cursor = it.split('\t')
        stringBuilder
            .append(cursor[AMIIBO_INDEX - 1])
            .append(",")
            .append(cursor[VILLAGER_NAME - 1].toLowerCase())
//            .append(",")
//            .append(cursor[text.SPECIES - 1])
            .append("\n")
    }

    val outputFile = File("D:\\modelmaker\\remoteCode\\Crawling\\outputText", "villagersNum201_400.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun writeVillager401to450() {
    val inputRawFile = File("D:\\modelmaker\\remoteCode\\Crawling\\raw\\villager_num_401_450_raw.txt")
    val stringBuilder = StringBuilder()
    var tempVillagerName = ""
    inputRawFile.forEachLine {
        if (it.isBlank()) {
            return@forEachLine
        }
        if (it.contains("No.")) {
            val index = it.replace("No.", "").trim().toInt() + 400
            stringBuilder
                .append(index)
                .append(",")
                .append(tempVillagerName.toLowerCase())
                .append("\n")
            return@forEachLine
        }
        tempVillagerName = it
    }

    println(stringBuilder.toString())

    val outputFile = File("D:\\modelmaker\\remoteCode\\Crawling\\outputText", "villagersNum401_450.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

const val AMIIBO_INDEX = 1
const val VILLAGER_NAME = 2
const val SPECIES = 3