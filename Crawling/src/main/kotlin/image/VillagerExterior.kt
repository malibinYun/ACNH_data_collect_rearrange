package image

import java.io.File
import java.net.URL
import javax.imageio.ImageIO

fun main() {
    saveVillagerExterior()
}

fun saveVillagerExterior() {
    System.setProperty("http.agent", "Chrome")
    val villagerNameIndexMap = getVillagerNameIndexMap()

    File("raw", "villager_exterior.txt").forEachLine {
        val cursor = it.split("\t")
        val nameEng = cursor[0].replace(" ", "").replace("'", "")
        val exteriorUrl = cursor[1]
        val villagerIndex = villagerNameIndexMap[nameEng]
        val exteriorImage = ImageIO.read(URL(exteriorUrl))
        ImageIO.write(
            exteriorImage,
            "png",
            File("extracted\\villager\\exterior\\villager_exterior_${"%03d".format(villagerIndex)}_$nameEng.png")
        )
        println("extracted\\villager\\exterior\\villager_exterior_${"%03d".format(villagerIndex)}_$nameEng.png")
    }

    "villager_exterior_000_name"
}


fun getVillagerNameIndexMap(): Map<String, Int> {
    val map = HashMap<String, Int>()
    File("fixed_data", "villagers_basic.txt").forEachLine {
        val cursor = it.replace("\uFEFF", "").split(",")
        val name = cursor[3].replace(" ", "").replace("'", "")
        map[name] = cursor[0].toInt()
    }
    return map
}
