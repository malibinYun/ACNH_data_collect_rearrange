package text

import java.io.File
import java.lang.StringBuilder

fun main() {
//    writeEnglishSpecies()
//    writeEnglishPersonality()
    writeVillagerSpeciesPhrases()
}

fun writeEnglishSpecies() {
    val villagers = getVillagerSpeciesPhrases()
    val stringBuilder = StringBuilder()

    val species = villagers
        .map { it.species }
        .distinct()
        .sorted()
    println(species.size)

    for (item in species) {
        stringBuilder.append(item).append("\n")
    }

    val outputFile = File("outputText", "villager_species.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun writeEnglishPersonality() {
    val stringBuilder = StringBuilder()
    val personality = getVillagerSpeciesPhrases()
        .map { it.personality }
        .distinct()
        .sorted()
    println(personality.size)

    for (item in personality) {
        stringBuilder.append(item).append("\n")
    }

    val outputFile = File("outputText", "villager_personalities.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

// 이걸로 영어 말버릇, 영어 성격, 영어 종 가져올 수 있음.
fun getVillagerSpeciesPhrases(): List<VillagerSpeciesPhrase> {
    val inputRawFile = File("raw\\주민종말버릇영어.txt")
    val villagerSpeciesPhrases = ArrayList<VillagerSpeciesPhrase>()

    inputRawFile.forEachLine {
        val cursor = it.split('\t')
        villagerSpeciesPhrases.add(
            VillagerSpeciesPhrase(
                name = cursor[0],
                personality = cursor[2]
                    .replace("♂ ", "")
                    .replace("♀ ", ""),
                species = cursor[3],
                phrase = cursor[5].replace("\"", "")
            )
        )
    }
//    for (item in villagerSpeciesPhrases) {
//        println(item)
//    }
    return villagerSpeciesPhrases
}

fun writeVillagerSpeciesPhrases() {
    val stringBuilder = StringBuilder()
    getVillagerSpeciesPhrases().forEach {
        stringBuilder
            .append(it.name).append(",")
            .append(it.species).append(",")
            .append(it.phrase).append("\n")
    }
    val outputFile = File("outputText", "villager_eng_species_phrases.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

data class VillagerSpeciesPhrase(
    val name: String,
    val personality: String,
    val species: String,
    val phrase: String
)