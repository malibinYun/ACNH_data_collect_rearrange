package com.malibin.acnh.wiki.data.upload

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

/**
 * Created By Malibin
 * on 5월 30, 2020
 */

fun Context.uploadHouseWare() {
    val csvTexts = File(getBaseFileLocation(), "catalog_가구.csv").useLines { it.toList() }
    for (i in 1 until csvTexts.size) {
        val cursor = splitCsv(csvTexts[i])

        uploadHouseWare(FHouseWare.create(cursor))
//        println(FHouseWare.create(cursor).makeFId())
    }
}

private fun uploadHouseWare(houseWare: FHouseWare) {
    FirebaseFirestore.getInstance()
        .collection("housewares")
        .document(houseWare.makeFId())
        .set(houseWare)
        .addOnSuccessListener { Log.d("Malibin Debug", "${houseWare.nameKor} 먼가 성공함") }
        .addOnFailureListener { showFailStackTrace(it) }
}

data class FHouseWare(
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
    val variantId: String?,
    val variation: String?,
    val bodyTitle: String?,
    val pattern: String?,
    val patternTitle: String?,
    val kitCost: Int?,
    val canBodyCustom: Boolean,
    val canPatternCustom: Boolean,
    val milesPrice: Int?,
    val canOutDoor: Boolean
) {
    fun makeFId(): String {
        return "${"%06d".format(id)}_${nameEng.toLowerCase()}_${variantId ?: ""}"
    }

    companion object {
        fun create(cursor: List<String>): FHouseWare {
            val colors = ArrayList<String>()
            if (cursor[8].isNotBlank()) colors.add(cursor[8])
            if (cursor[9].isNotBlank()) colors.add(cursor[9])
            return FHouseWare(
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
                variantId = naChecker(cursor[13]),
                variation = naChecker(cursor[14]),
                bodyTitle = naChecker(cursor[15]),
                pattern = naChecker(cursor[16]),
                patternTitle = naChecker(cursor[17]),
                kitCost = cursor[18].toIntOrNull(),
                canBodyCustom = cursor[19] == "Yes",
                canPatternCustom = cursor[20] == "Yes",
                milesPrice = cursor[21].toIntOrNull(),
                canOutDoor = cursor[22] == "Yes"
            )
        }
    }
}