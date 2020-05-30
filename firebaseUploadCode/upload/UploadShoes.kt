package com.malibin.acnh.wiki.data.upload

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

/**
 * Created By Malibin
 * on 5월 31, 2020
 */

fun Context.uploadShoes() {
    val csvTexts = File(getBaseFileLocation(), "catalog_신발.csv").useLines { it.toList() }
    for (i in 1 until csvTexts.size) {
        val cursor = splitCsv(csvTexts[i])

        uploadShoes(FShoes.create(cursor))
//        println(FShoes.create(cursor).makeFId())
    }
}

private fun uploadShoes(shoes: FShoes) {
    FirebaseFirestore.getInstance()
        .collection("shoes").document(shoes.makeFId())
        .set(shoes)
        .addOnSuccessListener { Log.d("Malibin Debug", "${shoes.nameKor} 먼가 성공함") }
        .addOnFailureListener { showFailStackTrace(it) }
}

data class FShoes(
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
    val closetImageUrl: String,
    val seasonalAvailability: String,
    val style: String,
    val labelThemes: List<String>,
    val variation: String,
    val canVillagerWear: Boolean,
    val milesPrice: Int?
) {
    fun makeFId(): String {
        return "${"%06d".format(id)}_${nameEng.toLowerCase()}_$variation"
    }

    companion object {
        fun create(cursor: List<String>): FShoes {
            val colors = ArrayList<String>()
            if (cursor[8].isNotBlank()) colors.add(cursor[8])
            if (cursor[9].isNotBlank()) colors.add(cursor[9])
            return FShoes(
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
                closetImageUrl = cursor[13],
                seasonalAvailability = cursor[14],
                style = cursor[15],
                labelThemes = cursor[16].split("#"),
                variation = cursor[17],
                canVillagerWear = cursor[18] == "Yes",
                milesPrice = cursor[19].toIntOrNull()
            )
        }
    }
}