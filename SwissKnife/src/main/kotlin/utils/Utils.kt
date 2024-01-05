import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(year: Int, day: String) = Path("src/main/resources/inputs/$year/Day$day.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Adds the specified number of zeroes to the beginning of the passed number and returns its string representation.
 */
fun addLeadingZero(number: Int, zeroes: Int = 1) : String {
    val buffer = StringBuilder()
    for (i in 0..<zeroes) buffer.append("0")
    buffer.append(number)
    return buffer.toString()
}
