package text

import java.io.File
import java.lang.StringBuilder

fun main() {
    val speciesRecords = File("fixed_data\\villager_species.txt").useLines { it.toList() }

    val engSpeciesPhraseRecords = File("outputText\\villager_eng_species_phrases.txt").useLines { it.toList() }
    val korPhraseCoffeeRecords = File("outputText\\villager_kor_phrases_coffee.txt").useLines { it.toList() }

    val basicRecords = File("outputText\\villagers_basic_database.txt").useLines { it.toList() }

    val stringBuilder = StringBuilder()
    for (i in korPhraseCoffeeRecords.indices) {
        val basicRecordCursor = basicRecords[i].split(",")
        val engSpeciesPhraseRecordCursor = engSpeciesPhraseRecords[i].split(",")
        val korPhraseCoffeeRecordCursor = korPhraseCoffeeRecords[i].split(",")

        val engName = basicRecordCursor[3]
        val searchedEngSpeciesPhraseRecordCursor = engSpeciesPhraseRecords.first { it.contains(engName) }.split(",")
        val engPhrase = searchedEngSpeciesPhraseRecordCursor[2]
        val engSpecies = searchedEngSpeciesPhraseRecordCursor[1]
        val korSpecies = speciesRecords.first { it.contains(engSpecies) }.split(",")[1]

        stringBuilder
            .append(basicRecordCursor[0]).append(",") // index
            .append(basicRecordCursor[1]).append(",") // nameKor
            .append(basicRecordCursor[2]).append(",") // nameJpn
            .append(basicRecordCursor[3]).append(",") // nameEng
            .append(basicRecordCursor[4]).append(",") // gender
            .append(basicRecordCursor[5]).append(",") // birthday
            .append(basicRecordCursor[6]).append(",") // personality
            .append(korSpecies).append(",") // species
            .append(korPhraseCoffeeRecordCursor[2]).append(",") // phraseKor
            .append(engPhrase).append(",") // phraseEng
            .append(korPhraseCoffeeRecordCursor[3]).append(",") // coffeeBeans
            .append(korPhraseCoffeeRecordCursor[4]).append(",") // milkQuantity
            .append(korPhraseCoffeeRecordCursor[5]).append("\n") // sugarCounts
    }
    println(stringBuilder.toString())

    val outputFile = File("fixed_data", "villagers.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}


data class Villager(
    val amiiboIndex: String,
    val nameKor: String,
    val nameJpn: String,
    val nameEng: String,
    val gender: String,
    val birthDay: String,
    val personality: String,
    val phraseKor: String,
    val phraseEng: String,
    val species: String,
    val coffeeBeans: String,
    val milkAmount: String,
    val sugarCount: Int
) {
    override fun toString(): String {
        return "$amiiboIndex,$nameKor,$nameJpn,$nameEng,$gender,$birthDay,$personality,$phraseKor,$phraseEng,$species,$coffeeBeans,$milkAmount,$sugarCount"
    }
}