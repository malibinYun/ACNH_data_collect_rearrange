package basedb

import java.io.File
import java.util.regex.Pattern

fun main() {
    val fossilMap = getFossilCsvMap()

    val stringBuilder = StringBuilder()
    var index = 1
    File("fossil", "fossil_urls.txt").forEachLine {
        val regex = "_(.*).png"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(it)
        matcher.find()
        val fossilName = matcher.group(1).split("_")[2]
        println(fossilName)
        val cursor = fossilMap[fossilName]!!
        val animalNameEng = cursor[0].split(" ")[0]
        val parts = cursor[0].split(" ").getOrNull(1) ?: "single"
        val price = cursor[1]
        val size = cursor[2]
        val location = cursor[3]
        val interAction = cursor[4]
        stringBuilder
            .append(index).append(",")
            .append(animalNameEng).append(",")
            .append(parts).append(",")
            .append(fossilName).append(",")
            .append(price).append(",")
            .append(size).append(",")
            .append(location).append(",")
            .append(interAction).append("\n")
    }
    println(stringBuilder.toString())
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
//https://firebasestorage.googleapis.com/v0/b/acnh-wiki-f8aa7.appspot.com/o/fossil%2Fic_fossil_001_acanthostega.png?alt=media&token=1b8b0b0c-b7a5-484d-8085-3a3631cdf64f
