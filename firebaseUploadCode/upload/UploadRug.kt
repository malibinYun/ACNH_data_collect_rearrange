package com.malibin.acnh.wiki.data.upload

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

/**
 * Created By Malibin
 * on 5월 27, 2020
 */

fun Context.uploadRug() {
    val csvTexts = File(getBaseFileLocation(), "catalog_러그.csv").useLines { it.toList() }
    for (i in 1 until csvTexts.size) {
        val cursor = splitCsv(csvTexts[i])

        uploadRug(FRug.create(cursor))
        println(FRug.create(cursor))
    }
}

private fun uploadRug(rug: FRug) {
    FirebaseFirestore.getInstance()
        .collection("rugs").document(rug.makeFId())
        .set(rug)
        .addOnSuccessListener { Log.d("Malibin Debug", "${rug.nameKor} 먼가 성공함") }
        .addOnFailureListener { showFailStackTrace(it) }
}

data class FRug(
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
    val hhaConcepts: List<String>,
    val hhaSeries: String,
    val milesPrice: Int?
) {
    fun makeFId(): String {
        return "${"%06d".format(id)}_$nameEng"
    }

    companion object {
        fun create(cursor: List<String>): FRug {
            val colors = ArrayList<String>()
            if (cursor[8].isNotBlank()) colors.add(cursor[8])
            if (cursor[9].isNotBlank()) colors.add(cursor[9])

            val hhaConcepts = ArrayList<String>()
            if (cursor[13].isNotBlank()) hhaConcepts.add(cursor[13])
            if (cursor[14].isNotBlank()) hhaConcepts.add(cursor[14])

            return FRug(
                nameKor = cursor[0],
                id = cursor[1].toInt(),
                nameEng = cursor[2],
                imageUrl = cursor[3],
                buyCost = cursor[4].toIntOrNull(),
                sellPrice = cursor[5].toInt(),
                source = cursor[6],
                sourceNote = cursor[7],
                colors = colors.distinct(),
                available = cursor[10],
                canDiy = cursor[11] == "Yes",
                size = cursor[12],
                hhaConcepts = hhaConcepts.distinct(),
                hhaSeries = cursor[15],
                milesPrice = cursor[16].toIntOrNull()
            )
        }
    }
}