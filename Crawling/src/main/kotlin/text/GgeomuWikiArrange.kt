package text

import java.io.File
import java.lang.StringBuilder

val VILLAGER_MAP = HashMap<String, Int>()

fun main() {
    loadVillagerMap()
    꺼무위키1()
    꺼무위키2()
}

fun loadVillagerMap() {
    val inputRawFile = File("D:\\modelmaker\\remoteCode\\Crawling\\raw\\villagerIndexMap.txt")
    inputRawFile.forEachLine {
        val cursor = it.split(",")
        VILLAGER_MAP[cursor[1]] = cursor[0].toInt()
    }
    VILLAGER_MAP["(없음)"] = -1
    println(VILLAGER_MAP)
}

fun 꺼무위키1() {
    val inputRawFile = File("D:\\modelmaker\\remoteCode\\Crawling\\raw\\GGEOMUWIKI1_raw.txt")
    val stringBuilder = StringBuilder()

    var currentCursor = 0

    inputRawFile.forEachLine {
        if (it.contains("파일")) {
            currentCursor = 0
        }
        when (currentCursor++) {
            NAME_ENG -> {
                val name = it.toLowerCase().replace(".", "")
                println(it)
                println(VILLAGER_MAP[name])
                stringBuilder.append(it).append(",")
                    .append(VILLAGER_MAP[name]).append(",")
            }
            NAME_KOR, NAME_JP -> {
                stringBuilder.append(it).append(",")
            }
            GENDER -> {
                val gender = if (it == "♂") "m" else if (it == "♀") "f" else "????"
                stringBuilder.append(gender).append(",")
            }
            BIRTH -> {
                stringBuilder.append(it.trim()).append(",")
            }
            PERSONALITY -> {
                stringBuilder.append(it).append("\n")
            }
            else -> {
            }
        }
    }

    println(stringBuilder.toString())

    val outputFile = File("D:\\modelmaker\\remoteCode\\Crawling\\outputText", "꺼무1.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun 꺼무위키2() {
    val inputRawFile = File("D:\\modelmaker\\remoteCode\\Crawling\\raw\\GGEOMUWIKI2_raw.txt")
    val stringBuilder = StringBuilder()

    var currentCursor = 1
    var wasOzone = false

    inputRawFile.forEachLine {
        if (wasOzone && !it.contains("O") && !it.contains("X") && !it.contains("◎") && !it.contains("☆") && !it.isBlank()) {
            currentCursor = 1
        }
        wasOzone = false
        when (currentCursor++) {
            NAME_ENG -> {
                val name = it.toLowerCase().replace(".", "")
                println(it)
                println(VILLAGER_MAP[name])
                stringBuilder.append(it).append(",")
                    .append(VILLAGER_MAP[name]).append(",")
            }
            NAME_KOR, NAME_JP -> {
                stringBuilder.append(it).append(",")
            }
            GENDER -> {
                val gender = if (it == "♂") "m" else if (it == "♀") "f" else "????"
                stringBuilder.append(gender).append(",")
            }
            BIRTH -> {
                stringBuilder.append(it.trim()).append(",")
            }
            PERSONALITY -> {
                stringBuilder.append(it).append("\n")
            }
            else -> {
                wasOzone = true
            }
        }
    }

    println(stringBuilder.toString())

    val outputFile = File("D:\\modelmaker\\remoteCode\\Crawling\\outputText", "꺼무2.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

const val NAME_KOR = 1
const val NAME_JP = 2
const val NAME_ENG = 3
const val GENDER = 4
const val BIRTH = 5
const val PERSONALITY = 6