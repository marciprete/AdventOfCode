 import it.senape.aoc.utils.Day

class Day08 : Day(2015, 8) {

    override fun part1(input: List<String>): Any? {
        return input.map { line ->
            line.length - (line.replace("\\\\x[0-9a-f]{2}".toRegex(), ".")
                .replace("\\\\[\\W]".toRegex(), ".").length - 2)
        }.sum()
    }


    override fun part2(input: List<String>): Any? {
        return input.map { line ->
            (line.replace("[\\W]".toRegex(), "00").length + 2) - line.length
        }.sum()
    }
}


fun main() = Day.solve(Day08(), 12, 19)
