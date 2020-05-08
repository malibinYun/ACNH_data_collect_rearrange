package basedb

import java.io.File

fun main() {

}

fun getBugIndexNameKorMap(): Map<Int, String> {
    val map = HashMap<Int, String>()
    File("raw", "bug_index_nameKor.txt").forEachLine {
        val cursor = it.split("\t")
        map[cursor[0].toInt()] = cursor[2]
    }
    return map
}