package basedb

import java.io.File
import java.util.regex.Pattern

fun main() {
    val fossilMap = getFossilCsvMap()

    val stringBuilder = StringBuilder("index,nameKor,partsKor,indexEngName,price,size,museumLocation,interact,imgUrl\n")
    var index = 1
    File("fossil", "fossil_urls.txt").forEachLine {
        val regex = "_(.*).png"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(it)
        matcher.find()
        val fossilEngFullName = matcher.group(1).split("_")[2]
        println(fossilEngFullName)
        val cursor = fossilMap[fossilEngFullName]!!
        val animalNameEng = cursor[0].split(" ")[0]
        val parts = cursor[0].split(" ").getOrNull(1) ?: "single"
        val price = cursor[1]
        val size = cursor[2]
        val location = cursor[3]
        val interAction = cursor[4]
        val nameKor = cursor[5]
        val partsKor = cursor[6]
        stringBuilder
            .append(index).append(",")
            .append(nameKor).append(",")
            .append(partsKor).append(",")
            .append(fossilEngFullName).append(",")
            .append(price).append(",")
            .append(size).append(",")
            .append(location).append(",")
            .append(interAction).append(",")
            .append(it).append("\n")
        index++
    }
    println(stringBuilder.toString())

    val outputFile = File("fixed_data", "fossils_complete.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun getFossilCsvMap(): Map<String, List<String>> {
    val fossilMap = HashMap<String, List<String>>()
    File("fossil", "fossils.csv").forEachLine {
        val cursor = it.split(",")
        val fossilName = cursor[0].replace("\\s".toRegex(), "")
        fossilMap[fossilName] = cursor
//        println("$fossilName : $cursor")
    }
    return fossilMap
}

fun getFossilUrlMap(): List<String> {
    val fossilList = ArrayList<String>()
    File("fossil", "fossil_urls.txt").forEachLine {
        val regex = "_(.*).png"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(it)
        matcher.find()
        val fossilName = matcher.group(1)
        fossilList.add(it)

        println("$fossilName : $it")
    }
    return fossilList
}
