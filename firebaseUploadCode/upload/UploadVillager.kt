package com.malibin.acnh.wiki.data.upload

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

/**
 * Created By Yun Hyeok
 * on 5월 21, 2020
 */

fun Context.uploadVillagers() {
    val csvTexts = File(getBaseFileLocation(), "villager_complete.csv").useLines { it.toList() }
    for (i in 1 until csvTexts.size) {
        val cursor = splitCsv(csvTexts[i])
        uploadVillager(FVillager.create(cursor))
    }
}

private fun uploadVillager(villager: FVillager) {
    FirebaseFirestore.getInstance()
        .collection("villagers").document(villager.makeFId())
        .set(villager)
        .addOnSuccessListener { Log.d("Malibin Debug", "${villager.amiiboIndex} 먼가 성공함") }
        .addOnFailureListener { showFailStackTrace(it) }
}

data class FVillager(
    val amiiboIndex: Int,
    val nameKor: String,
    val nameJpn: String,
    val nameEng: String,
    val gender: String,
    val birth: String,
    val personality: String,
    val species: String,
    val phraseKor: String,
    val phraseEng: String,
    val coffeeBeans: String,
    val milkAmount: String,
    val sugarCount: Int,
    val hobby: String,
    val likeMusic: String,
    val likeStyles: List<String>,
    val likeColors: List<String>,
    val wallPaper: String,
    val floor: String,
    val furnitureIds: List<Int>,
    val amiiboCardUrl: String,
    val detailUrl: String,
    val exteriorUrl: String,
    val interiorUrl: String,
    val iconUrl: String,
    val posterUrl: String
) {
    fun makeFId(): String {
        return "${"%03d".format(amiiboIndex)}_$nameEng"
    }

    companion object {
        fun create(cursor: List<String>) = FVillager(
            cursor[0].toInt(),
            cursor[1],
            cursor[2],
            cursor[3],
            cursor[4],
            cursor[5],
            cursor[6],
            cursor[7],
            cursor[8],
            cursor[9],
            cursor[10],
            cursor[11],
            cursor[12].toInt(),
            cursor[13],
            cursor[14],
            cursor[15].split("#").distinct(),
            cursor[16].split("#").distinct(),
            cursor[17],
            cursor[18],
            cursor[19].split(";").map { it.toInt() }.sorted(),
            cursor[20],
            cursor[21],
            cursor[22],
            cursor[23],
            cursor[24],
            cursor[25]
        )
    }
}