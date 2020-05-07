import java.lang.StringBuilder

fun joinListWithComma(list: List<String>): String {
    val stringBuilder = StringBuilder(list[0])
    for (i in 1 until list.size) {
        stringBuilder.append(",").append(list[i])
    }
    return stringBuilder.toString()
}