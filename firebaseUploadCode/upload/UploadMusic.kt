package com.malibin.acnh.wiki.data.upload

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

/**
 * Created By Malibin
 * on 5월 30, 2020
 */

fun Context.uploadMusic() {
    val csvTexts = File(getBaseFileLocation(), "catalog_음악.csv").useLines { it.toList() }
    for (i in 1 until csvTexts.size) {
        val cursor = splitCsv(csvTexts[i])

        uploadMusic(FMusic.create(cursor))
        println(FMusic.create(cursor))
    }
}

private fun uploadMusic(music: FMusic) {
    FirebaseFirestore.getInstance()
        .collection("musics").document(music.makeFId())
        .set(music)
        .addOnSuccessListener { Log.d("Malibin Debug", "${music.nameKor} 먼가 성공함") }
        .addOnFailureListener { showFailStackTrace(it) }
}

data class FMusic(
    val nameKor: String,
    val id: Int,
    val nameEng: String,
    val imageUrl: String,
    val buyCost: Int?,
    val sellPrice: Int,
    val source: String,
    val sourceNote: String,
    val colors: List<String>,
    val available: String,
    val canDiy: Boolean,
    val size: String,
    val albumImageUrl: String
) {
    fun makeFId(): String {
        return "${"%06d".format(id)}_Music"
    }

    companion object {
        fun create(cursor: List<String>): FMusic {
            val colors = ArrayList<String>()
            if (cursor[8].isNotBlank()) colors.add(cursor[8])
            if (cursor[9].isNotBlank()) colors.add(cursor[9])

            return FMusic(
                nameKor = cursor[0],
                id = cursor[1].toInt(),
                nameEng = cursor[2],
                imageUrl = cursor[3],
                buyCost = cursor[4].toIntOrNull(),
                sellPrice = cursor[5].toInt(),
                source = cursor[6].replace("#", ","),
                sourceNote = cursor[7].replace("#", ","),
                colors = colors.distinct(),
                available = cursor[10],
                canDiy = cursor[11] == "Yes",
                size = cursor[12],
                albumImageUrl = cursor[13]
            )
        }
    }
}