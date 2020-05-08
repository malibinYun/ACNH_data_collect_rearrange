package image

import org.jsoup.Jsoup
import java.io.File
import java.net.URL
import java.util.regex.Pattern
import javax.imageio.ImageIO

fun main() {
    saveBugDetailImages()
//    val siteUrl = "https://animalcrossing.fandom.com/wiki/Bugs_(New_Horizons)"
//    val connection = Jsoup.connect(siteUrl)
//
//    val document = connection.get()
//        .select("a.image-thumbnail:not(.link-internal)")
//        .select("a")
//
//    for (i in 0 until document.size) {
//        val element = document[i]
//        val url = element.attr("href").toString()
//        println(url)
//
//        val regex = "NH-Icon-(.*)\\.png"
//        val pattern = Pattern.compile(regex)
//        val matcher = pattern.matcher(url)
//
//        if (matcher.find()) {
//            val fishName = matcher.group(1)
//            val image = ImageIO.read(URL(url))
//
//            val savedPath = "D:\\modelmaker\\remoteCode\\Crawling\\extracted\\bug"
//            val index = "%03d".format(i + 1)
//
//            ImageIO.write(image, "png", File("$savedPath\\ic_bug_${index}_$fishName.png"))
//        }
//    }
}


fun saveBugDetailImages() {
    System.setProperty("http.agent", "Chrome")

    var isFirst = true
    File("raw", "bugCsv.csv").forEachLine {
        if (isFirst) {
            isFirst = false
            return@forEachLine
        }
        val cursor = it.split(",")
        val index = cursor[0].toInt()
        val nameEng = cursor[1]
            .replace("\\s".toRegex(), "")
            .replace("'", "")
            .replace("-", "")
            .toLowerCase()
        val detailImageUrl = cursor[2]

        val savePath = "extracted\\bug\\detail"
        val image = ImageIO.read(URL(detailImageUrl))
//        println("$savePath\\bug_detail_${"%03d".format(index)}_$nameEng.png")
        ImageIO.write(image, "png", File("$savePath\\bug_detail_${"%03d".format(index)}_$nameEng.png"))
    }
    println("Job Done")
}
