package image

import java.io.File
import java.net.URL
import javax.imageio.ImageIO

fun main() {
    saveReactions()
}

fun saveReactions() {
    System.setProperty("http.agent", "Chrome")

    val fileTexts = File("raw", "reactionCsv.csv").useLines { it.toList() }

    for (i in 1 until fileTexts.size) {
        val cursor = fileTexts[i].split(",")
        val reactionName = cursor[0]
        val imageUrl = cursor[1]

        val reactionImage = ImageIO.read(URL(imageUrl))
        ImageIO.write(reactionImage, "png", File("extracted\\reaction\\reaction_$reactionName.png"))
        println("extracted\\reaction\\reaction_$reactionName.png")
    }
}
