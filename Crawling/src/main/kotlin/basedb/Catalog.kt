package basedb

import java.io.File
import java.lang.StringBuilder

val catalogTranslateList = listOf(
    "가방", "도구", "러그", "모자", "바닥", "벽지", "사진", "상의", "신발", "액세서리"
    , "양말", "우산", "울타리", "음악", "포스터", "하의", "한벌옷"
)

//가구 : 벽걸이/잡화
fun main() {

//    bindNameKor("러그","러그")
//    for (kind in catalogTranslateList) {
//        bindNameKor(kind, kind)
//    }
//    bindFurnitureNameKor()
    bindNameKor("가구", "가구")
    bindNameKor("신발","신발")
    bindNameKor("음악","음악")

}

fun bindFurnitureNameKor() {
    bindNameKor("가구", "가구")
    bindNameKor("벽걸이", "가구")
    bindNameKor("잡화", "가구")
}

fun bindNameKor(kind: String, translateKind: String) {
    val stringBuilder = StringBuilder()
    val translateMap = getTranslateMap(translateKind)
    File("raw\\catalog", "$kind.csv").forEachLine {
        val nameEng = it.replace("\uFEFF", "").split(",")[1]
        val nameKor = translateMap[nameEng]
        stringBuilder
            .append(nameKor).append(",")
            .append(it).append("\n")

        if (nameKor == null) {
            println("kind = $kind : $nameKor,$it\n")
        }
    }

    val outputFile = File("fixed_data\\catalog", "catalog_$kind.txt")
    outputFile.writeText(stringBuilder.toString())
    File("fixed_data\\catalog", "catalog_$kind.csv").writeText(stringBuilder.toString())
    println("Write Success")
}


fun getTranslateMap(kind: String): Map<String, String> {
    val map = HashMap<String, String>()
    map["Name"] = "nameKor"
    File("raw\\catalog\\번역", "${kind}번역.csv").forEachLine {
        val cursor = it.split(",")
        map[cursor[2]] = cursor[14]
    }
    return map
}