package basedb

import java.io.File
import java.lang.StringBuilder

fun main() {

    val additionalMap = villagerNameAdditionalInfoMap()
    val musicKor = getTranslateMap("음악")
    val wallKor = getTranslateMap("벽지")
    val floorKor = getTranslateMap("바닥")
    val amiiboCardUrl = getVillagerIndexImageUrlMap("amiibo_card")
    val detailUrl = getVillagerIndexImageUrlMap("detail")
    val exteriorUrl = getVillagerIndexImageUrlMap("exterior")
    val interiorUrl = getVillagerIndexImageUrlMap("interior")
    val iconUrl = getVillagerIndexImageUrlMap("icon")
    val posterUrl = getVillagerIndexImageUrlMap("poster")

    val stringBuilder = StringBuilder()
    File("fixed_data", "villagers_basic.txt").forEachLine {
        val index = it.replace("\uFEFF", "").split(",")[0].toInt()
        val nameEng = it.split(",")[3]
        val additionalInfo = additionalMap[nameEng]!!
        val hobby = additionalInfo[1]
        val song = musicKor[additionalInfo[2]]
        val style = "${additionalInfo[3]}#${additionalInfo[4]}"
        val color = "${additionalInfo[5]}#${additionalInfo[6]}"
        val wall = wallKor[additionalInfo[7]]
        val floor = floorKor[additionalInfo[8]]
        val furnitureList = additionalInfo[9]
        stringBuilder
            .append(it).append(",")
            .append(hobby).append(",")
            .append(song).append(",")
            .append(style).append(",")
            .append(color).append(",")
            .append(wall).append(",")
            .append(floor).append(",")
            .append(furnitureList).append(",")
            .append(amiiboCardUrl[index]).append(",")
            .append(detailUrl[index]).append(",")
            .append(exteriorUrl[index]).append(",")
            .append(interiorUrl[index]).append(",")
            .append(iconUrl[index]).append(",")
            .append(posterUrl[index]).append("\n")
    }

    val outputFile = File("fixed_data", "villager_complete.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
    File("fixed_data", "villager_complete.csv").writeText(stringBuilder.toString())
}

fun villagerNameAdditionalInfoMap(): Map<String, List<String>> {
    val map = HashMap<String, List<String>>()
    File("raw", "주민추가정보.txt").forEachLine {
        val cursor = it.replace("\uFEFF", "").split(",")
        map[cursor[0]] = cursor
    }
    return map
}

fun getVillagerIndexImageUrlMap(kind: String): Map<Int, String> {
    val map = HashMap<Int, String>()
    File("raw", "villager_${kind}_urls.txt").forEachLine {
        val cursor = it.replace("\uFEFF", "").split(",")
        map[cursor[0].toInt()] = cursor[1]
    }
    return map
}

