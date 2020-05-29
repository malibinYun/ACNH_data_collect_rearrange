package text

import java.io.File

fun main() {

    val records = File("D:\\modelmaker\\remoteCode\\ACNH\\Git Remote\\resources\\카탈로그\\다른분\\원본", "가구.txt")
        .useLines { it.toList() }


    val size = records[0]
        .replace("\"", "")
        .split("\t")
        .size
    for (record in records) {
        val cursor = record.replace("\"", "")
            .split("\t")
//        println(record)
        if (cursor.size != size) {
            println(cursor.size)
        }

    }
}
