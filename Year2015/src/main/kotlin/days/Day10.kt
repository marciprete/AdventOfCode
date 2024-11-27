import it.senape.aoc.utils.Day

val regex = "([\\d])\\1*".toRegex()

class Day10(year: Int, day: Int) : Day(year, day) {
    override fun part1(input: List<String>): Any? {
        var n = input[0]

        return lookAndSay(n, 40)
    }

    override fun part2(input: List<String>): Any? {
        var n = input[0]
        return lookAndSay(n, 50)
    }

    private fun lookAndSay(input: String, iterations: Int): Int {
        var n = input
        for (j in 0..iterations - 1) {
            n = regex.findAll(n).map { it.value.length.toString() + it.groupValues[1] }.joinToString("")
        }
        println(n.length)
        return n.length
    }
}

fun main() = Day.solve(Day10(2015, 10), 82350, 1166642)
