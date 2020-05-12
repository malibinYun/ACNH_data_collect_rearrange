package image

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.WebDriverWait
import text.Villager
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO

val WEB_DRIVER_ID = "webdriver.chrome.driver"
val WEB_DRIVER_PATH = "chromedriver.exe"

var villagerMap: Map<String, Villager> = HashMap()

fun main() {
    System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH)
    villagerMap = createVillagerNameKorVillagerMap()

    File("raw", "모동숲 주민 인테리어 모음 표.csv").forEachLine {
        val cursor = it.replace("\uFEFF", "").split(",")
        println(cursor)
        val villager = villagerMap[cursor[0]]!!
        val url = cursor[1]
        saveImageOfTwitter(villager, url)
    }
}

fun createVillagerNameKorVillagerMap(): Map<String, Villager> {
    val map = HashMap<String, Villager>()
    File("fixed_data", "villagers_basic.txt").forEachLine {
        val cursor = it.replace("\uFEFF", "").split(",")
        val villager = Villager.of(cursor)
        map[villager.nameKor] = villager
    }
    return map
}

fun saveImageOfTwitter(villager: Villager, url: String) {
    if (url.isBlank()) return
    val siteUrl = "$url/photo/1"
    val driver = ChromeDriver()

//    driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS)
    driver.get(siteUrl)

    Thread.sleep(2500)
    val elements = driver.findElementsByCssSelector(".css-9pa8cd")
    var imageUrl: String? = ""

    for (element in elements) {
        if (element.getAttribute("alt") != "이미지") continue
        val rawUrl = element.getAttribute("src")

        val regex = Regex("(.*)\\?format=(jpg|png)")
        val matchResult = regex.find(rawUrl)
        imageUrl = matchResult?.value
        println(imageUrl)
    }
    val amiiboIndex = villager.amiiboIndex.toInt()
    val nameEng = villager.nameEng.replace(" ", "").replace("'", "")
    println("extracted\\villager\\interior\\villager_interior_${"%03d".format(amiiboIndex)}_$nameEng.png")
    println()

    val image = ImageIO.read(URL(imageUrl))
    ImageIO.write(
        image,
        "png",
        File("extracted\\villager\\interior\\villager_interior_${"%03d".format(amiiboIndex)}_$nameEng.png")
    )

    driver.close()
}