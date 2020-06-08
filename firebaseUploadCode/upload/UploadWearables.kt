package com.malibin.acnh.wiki.data.upload

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.malibin.acnh.wiki.data.entity.wearable.Wearable
import java.io.File

/**
 * Created By Malibin
 * on 6월 08, 2020
 */

private val wearableList = Wearable.Type.values()
private val fileNameMap = mapOf(
    Pair("tops", "상의"),
    Pair("bottoms", "하의"),
    Pair("onepieces", "한벌옷"),
    Pair("headwears", "모자"),
    Pair("accessories", "액세서리"),
    Pair("socks", "양말"),
    Pair("shoes", "신발"),
    Pair("bags", "가방"),
    Pair("umbrellas", "우산")
)

fun Context.uploadWearableOf() {
    for (type in wearableList) {
        val korFileName = "catalog_${fileNameMap[type.name.toLowerCase()]}.csv"
        val csvTexts =
            File(getBaseFileLocation(), korFileName).useLines { it.toList() }
        for (i in 1 until csvTexts.size) {
            val cursor = splitCsv(csvTexts[i])
            println(cursor)
//            uploadWearableOf(type, FWearable.create(cursor))
//        println(FWearable.create(cursor).makeFId())
        }
    }

}

private fun uploadWearableOf(type: Wearable.Type, wearable: FWearable) {
    FirebaseFirestore.getInstance()
        .collection(type.name.toLowerCase()).document(wearable.makeFId())
        .set(wearable)
        .addOnSuccessListener { Log.d("Malibin Debug", "${wearable.nameKor} 먼가 성공함") }
        .addOnFailureListener { showFailStackTrace(it) }
}

data class FWearable(
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
        fun create(cursor: List<String>): FWearable {
            val colors = ArrayList<String>()
            if (cursor[8].isNotBlank()) colors.add(cursor[8])
            if (cursor[9].isNotBlank()) colors.add(cursor[9])
            return FWearable(
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