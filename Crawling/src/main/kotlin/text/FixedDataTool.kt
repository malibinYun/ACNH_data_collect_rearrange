package text

import java.io.File

fun main() {
    println(getPersonalities())
    println(getSpecies())
}

fun getPersonalities(): List<Personality> {
    val personalities = ArrayList<Personality>()
    File("fixed_data\\villager_personalities.txt").forEachLine {
        val cursor = it.split(',')
        personalities.add(
            Personality(
                eng = cursor[0],
                kor = cursor[1],
                jpn = cursor[2]
            )
        )
    }
    return personalities
}

fun getSpecies(): List<Species> {
    val species = ArrayList<Species>()
    File("fixed_data\\villager_species.txt").forEachLine {
        val cursor = it.split(',')
        species.add(
            Species(
                eng = cursor[0],
                kor = cursor[1]
            )
        )
    }
    return species
}

fun getSpeciesMap(): Map<String, String> {
    val speciesMap = HashMap<String, String>()
    getSpecies().forEach {
        speciesMap[it.eng] = it.kor
    }
    return speciesMap
}

data class Personality(
    val eng: String,
    val kor: String,
    val jpn: String
)

data class Species(
    val eng: String,
    val kor: String
)