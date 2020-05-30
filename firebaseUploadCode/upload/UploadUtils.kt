package com.malibin.acnh.wiki.data.upload

import android.content.Context
import android.text.TextUtils
import android.util.Log

/**
 * Created By Yun Hyeok
 * on 5월 22, 2020
 */

fun showFailStackTrace(it: Throwable) {
    Log.d(
        "Malibin Debug",
        "먼가 실패함 ${it.message} \n ${it.localizedMessage} \n ${it.cause} \n ${TextUtils.join(
            "\n",
            it.stackTrace
        )}"
    )
}

fun splitCsv(csv: String): List<String> =
    csv.replace("\uFEFF", "")
        .split(",")


fun Context.getBaseFileLocation() = getExternalFilesDir(null)?.absolutePath

fun naChecker(string: String): String? {
    return if(string == "NA") null
    else string
}
