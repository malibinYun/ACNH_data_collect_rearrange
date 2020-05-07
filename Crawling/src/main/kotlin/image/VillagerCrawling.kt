package image

import org.jsoup.Jsoup
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

fun main() {
    val siteUrl =
        "https://namu.wiki/w/%EB%8F%99%EB%AC%BC%EC%9D%98%20%EC%88%B2%20%EC%8B%9C%EB%A6%AC%EC%A6%88/%EC%9D%BC%EB%B0%98%20%EC%A3%BC%EB%AF%BC#s-4.10"

    System.setProperty("http.agent", "Chrome")

    val connection = Jsoup.connect(siteUrl)
    val document = connection.get()
        .select(".wiki-image:not(.wiki-image-loading)")

    for (i in 0 until document.size) {
        val element = document[i]
        val url = "https://" + element.attr("src").substring(2)
        val name = filterString(element.attr("alt"))

        val index = "%03d".format(i)
        val savedPath = "D:\\modelmaker\\remoteCode\\Crawling\\extracted\\villager\\detail"
        val image = ImageIO.read(URL(url))
        ImageIO.write(image, "png", File("$savedPath\\${index}_villager_$name.png"))

        println("${index}_villager_$name.png")
        println(url)
    }
}

fun filterString(input: String): String {
    var rawName = input.replace("NLa", "")
    rawName = rawName.replace("HD-1", "")
    rawName = rawName.replace("NH_1", "")
    rawName = rawName.replace("NH_2", "")
    rawName = rawName.replace("동숲", "")
    rawName = rawName.replace("NH", "")
    rawName = rawName.replace("NL", "")
    rawName = rawName.replace("DnMe+2", "")
    rawName = rawName.replace("DnMe+", "")
    rawName = rawName.replace("CF", "")
    rawName = rawName.replace("597px", "")
    rawName = rawName.replace("534px", "")
    rawName = rawName.replace("492px", "")
    rawName = rawName.replace("450px", "")
    rawName = rawName.replace("472px", "")
    rawName = rawName.replace("592px", "")
    rawName = rawName.replace("566px", "")
    rawName = rawName.replace("PG", "")
    rawName = rawName.replace("100px", "")
    rawName = rawName.replace("200px", "")
    rawName = rawName.replace("300px", "")
    rawName = rawName.replace("428px", "")
    rawName = rawName.replace("426px", "")
    rawName = rawName.replace("744px", "")
    rawName = rawName.replace("175px", "")
    rawName = rawName.replace("263px", "")
    rawName = rawName.replace("-", "")
    rawName = rawName.replace("_", "")
    rawName = rawName.replace("파일:", "")
    rawName = rawName.replace(".png", "")
    rawName = rawName.trim()
    rawName = rawName.toLowerCase()
    return rawName
}

