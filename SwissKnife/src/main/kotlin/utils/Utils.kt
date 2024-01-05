import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(year: Int, day: String) = Path("src/main/resources/inputs/$year/Day$day.txt").readLines()

fun addLeadingZero(number: Int, zeroes: Int = 1) : String {
    val buffer = StringBuilder()
    for (i in 0..<zeroes) buffer.append("0")
    buffer.append(number)
    return buffer.toString()
}