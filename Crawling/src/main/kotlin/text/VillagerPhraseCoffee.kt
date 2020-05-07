package text

import java.io.File
import java.lang.StringBuilder

fun main() {
    writeKorVillagerPhraseCoffee()
//    val villagersIndexes = 주민Index목록()
//    getVillagerPhraseCoffeeList()
//        .sortedBy { it.index }
//        .forEach { villagerPhraseCoffee ->
//            villagersIndexes.removeIf { it.toInt() == villagerPhraseCoffee.index }
//            println(villagerPhraseCoffee)
//        }
//
//    villagersIndexes.forEach {
//        println(it)
//    }
//    val villagerPhraseCoffeeList = getVillagerPhraseCoffeeList().sortedBy { it.index }
//    var i = 0
//    File("outputText\\villagers_basic_database.txt").forEachLine {
//        val cursor = it.split(",")
//        val stringBuilder = StringBuilder("origin : ")
//            .append(cursor[1])
//            .append("/// 비교 : ")
//            .append(villagerPhraseCoffeeList[i].korName)
//        if (cursor[1] == villagerPhraseCoffeeList[i++].korName) {
//            stringBuilder.append("##GOOD")
//        }
//        println(stringBuilder.toString())
//    }

}

fun writeKorVillagerPhraseCoffee(){
    val stringBuilder = StringBuilder()
    getVillagerPhraseCoffeeList().forEach {
        stringBuilder
            .append(it.index).append(",")
            .append(it.korName).append(",")
            .append(it.korPhrase).append(",")
            .append(it.coffeeBeans).append(",")
            .append(it.milk).append(",")
            .append(it.sugar).append("\n")
    }
    val outputFile = File("outputText", "villager_kor_phrases_coffee.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun getVillagerPhraseCoffeeList(): List<VillagerPhraseCoffee> {
    val villagerPhraseCoffeeList = ArrayList<VillagerPhraseCoffee>()
    File("raw\\villager_kor_phrase_coffee.txt").forEachLine {
        val cursor = it.split(',')

        val coffeeString = cursor[4].trim().split("/")
        val index =
            when {
                cursor[1].contains("S") -> return@forEachLine
                cursor[1].contains("-") -> return@forEachLine
                else -> cursor[1].replace("+", "4").toInt()
            }
        villagerPhraseCoffeeList.add(
            VillagerPhraseCoffee(
                index = index,
                korName = cursor[2],
                korPhrase = cursor[3],
                coffeeBeans = extractCoffeeBeans(coffeeString[0]),
                milk = extractMilk(coffeeString[0]).trim(),
                sugar = coffeeString[1].replace("개", "").trim().toInt()
            )
        )
    }
    return villagerPhraseCoffeeList.sortedBy { it.index }
}

fun extractCoffeeBeans(input: String): String {
    if (input.contains(BLAND)) return BLAND
    if (input.contains(MOCA)) return MOCA
    if (input.contains(KILI)) return KILI
    if (input.contains(BLUE)) return BLUE
    return "???"
}

fun extractMilk(input: String): String {
    return input.replace(BLAND, "")
        .replace(MOCA, "")
        .replace(KILI, "")
        .replace(BLUE, "")
}

data class VillagerPhraseCoffee(
    val index: Int,
    val korName: String,
    val korPhrase: String,
    val coffeeBeans: String,
    val milk: String,
    val sugar: Int
)

const val BLAND = "블렌드"
const val MOCA = "모카"
const val KILI = "킬리만자로"
const val BLUE = "블루마운틴"