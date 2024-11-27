import it.senape.aoc.utils.Day


class Day11(year: Int, day: Int) : Day(year, day) {
    private val letters = hashSetOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',  'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')
    private val sameCharRegexp = "(\\w)\\1".toRegex()
    private val forbiddenCharsRegepx = "[ilo]".toRegex()
    private var password = ""
    override fun part1(input: List<String>): Any? {
        password = input[0]
        do {
            password = incrementChar(password)

        } while (!checkRules(password))
        println(password)
        return password
    }

    override fun part2(input: List<String>): Any? {
        val part2 = part1(listOf(part1(input).toString()))
        return part2
    }
    
    fun incrementChar(string: String) : String {
        val next = letters.next(string.last())
        val replacement = string.dropLast(1)
        return (if (next != 'a') replacement else incrementChar(replacement)) + next
    }
    
    fun checkRules(string: String): Boolean {

        if (string.contains(forbiddenCharsRegepx)) return false
        val values = string.map{ char -> letters.lastIndexOf(char) }
        val increasingStraight = values.windowed(3).map{ it[2]-it[1] == 1 && it[1]-it[0] == 1 }
        val hasOverlappingPairs = sameCharRegexp.findAll(string).map { it.value }.toSet().size > 1
        return hasOverlappingPairs && increasingStraight.contains(true)
    }
    
    fun Set<Char>.next(obj: Char): Char {
        var list = this.toTypedArray()
        return list[(list.lastIndexOf(obj)+1)%list.size]
    }
}

fun main() = Day.solve(Day11(2015, 11), "ghjaabcc", "ghjbbcdd")
