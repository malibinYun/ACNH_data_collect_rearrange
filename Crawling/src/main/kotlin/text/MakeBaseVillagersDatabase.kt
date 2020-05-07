package text

import java.io.File
import java.lang.StringBuilder

fun main() {
    꺼무가공한거정렬하기()
}

fun 꺼무가공한거정렬하기() {
    val inputRawFile = File("D:\\modelmaker\\remoteCode\\Crawling\\raw\\꺼무합친거.txt")

    val data = ArrayList<List<String>>()
    inputRawFile.forEachLine {
        val record = it.split(",")
        data.add(record)
    }
    val sortedRecords = data.sortedBy { if (it[3] == "null") 999 else it[3].toInt() }

//    val 없는목록 = 주민Index목록()

    val stringBuilder = StringBuilder()
    for (record in sortedRecords) {
//        없는목록.remove(record[3])
        print("${record[3]} : $record")
        println()
        stringBuilder
            .append(record[3])
            .append(",")
            .append(record[0])
            .append(",")
            .append(record[1])
            .append(",")
            .append(record[2])
            .append(",")
            .append(record[4])
            .append(",")
            .append(record[5])
            .append(",")
            .append(record[6])
            .append("\n")
    }
//    for (index in 없는목록) {
//        print(index)
//        println()
//    }
    val outputFile = File("D:\\modelmaker\\remoteCode\\Crawling\\outputText", "villagers_basic_database.txt")
    outputFile.writeText(stringBuilder.toString())
    println("Write Success")
}

fun 주민Index목록(): ArrayList<String> {
    val list = ArrayList<String>()
    for (i in 18..100) {
        list.add(i.toString())
    }
    for (i in 118..200) {
        list.add(i.toString())
    }
    for (i in 218..300) {
        list.add(i.toString())
    }
    for (i in 317..450) {
        list.add(i.toString())
    }
    for (i in 501..508) {
        list.add(i.toString())
    }
    return list
}