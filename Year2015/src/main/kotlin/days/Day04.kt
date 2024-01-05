
import it.senape.aoc.utils.Day

class Day04 : Day(2015, 4) {

    override fun part1(input: List<String>): Any? {
        val s = input[0]
        return generateSequence(1) { it+1 }.first {(s+it).md5().startsWith("00000")}
    }

    override fun part2(input: List<String>): Any? {
        val s = input[0]
        return generateSequence(1) { it+1 }.first {(s+it).md5().startsWith("000000")}
    }
}

fun main() = Day.solve(Day04(), 609043, 6742839)
