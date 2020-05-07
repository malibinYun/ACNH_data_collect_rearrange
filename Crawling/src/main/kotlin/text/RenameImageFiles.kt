package text

import java.io.File

fun main() {
//    renameVillagerDetailImages()
    renameVillagerPosterImages()
}

fun renameVillagerDetailImages() {
    val villagerMap = getNameIndexVillagerMap()
    val villagerKeys = villagerMap.keys.toList()
    for (i in villagerKeys.indices) {
        val name = villagerKeys[i]
        val index = villagerMap[name]!!

        val wantToRename = File("images\\villager_detail", "villager_$name.png")
        val renamedFile = File("images\\villager_detail", "villager_detail_${"%03d".format(index)}_$name.png")
        wantToRename.renameTo(renamedFile)
    }
}

fun renameVillagerPosterImages() {
    val villagerMap = getNameIndexVillagerMap()
    val villagerKeys = villagerMap.keys.toList()
    for (i in villagerKeys.indices) {
        val name = villagerKeys[i]
        val index = villagerMap[name]!!

        val wantToRename = File("images\\villager_poster", "poster_villager_${name.replace(" ", "_")}.png")
        val renamedFile = File("images\\villager_poster", "villager_poster_${"%03d".format(index)}_${name.replace("_","")}.png")
        wantToRename.renameTo(renamedFile)
    }
}

fun getIndexNameVillagerMap(): Map<Int, String> {
    val map = HashMap<Int, String>()

    File("fixed_data", "villagers_basic.txt").forEachLine {
        val cursor = it.replace("\uFEFF", "").split(",")
        val index = cursor[0].toInt()
        var engName = cursor[3].replace("'", "")
        engName = engName[0].toLowerCase() + engName.substring(1 until engName.length)

        map[index] = engName
    }
    return map
}

fun getNameIndexVillagerMap(): Map<String, Int> {
    val map = HashMap<String, Int>()

    File("fixed_data", "villagers_basic.txt").forEachLine {
        val cursor = it.replace("\uFEFF", "").split(",")
        val index = cursor[0].toInt()
        var engName = cursor[3].replace("'", "")
//        engName = engName[0].toLowerCase() + engName.substring(1 until engName.length)

        map[engName] = index
    }
    return map
}