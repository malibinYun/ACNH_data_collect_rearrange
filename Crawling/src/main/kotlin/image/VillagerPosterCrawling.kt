package image

import org.jsoup.Jsoup
import java.io.File
import java.net.URL
import java.util.regex.Pattern
import javax.imageio.ImageIO

fun main() {
    val siteUrl = "https://animalcrossing.fandom.com/wiki/Villager_list_(New_Horizons)"
    val connection = Jsoup.connect(siteUrl)

    val document = connection.get()
        .select("a.image-thumbnail:not(.link-internal)")
        .select("a")

    for (i in 0 until document.size) {
        val element = document[i]
        val url = element.attr("href").toString()
        println(url)

        val regex = "NH-(.*)_poster\\.png"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(url)

        if (matcher.find()) {
            val fishName = matcher.group(1)
            val image = ImageIO.read(URL(url))

            val savedPath = "D:\\modelmaker\\remoteCode\\Crawling\\extracted\\poster"

            ImageIO.write(image, "png", File("$savedPath\\poster_villager_$fishName.png"))
            println("poster_villager_$fishName.png")
        }
    }
}