
import data.Coords
import it.senape.aoc.utils.Day

class Day03 : Day(2015, 3) {

    private fun toCoords(origin: Coords, char: Char) : Coords {
        return when (char) {
            '^' -> Coords(origin.x, origin.y+1)
            'v' -> Coords(origin.x, origin.y-1)
            '>' -> Coords(origin.x+1, origin.y)
            '<' -> Coords(origin.x-1, origin.y)
            else -> error("Unexpected")
        }
    }
    override fun part1(input: List<String>): Any? {
        return input[0].runningFold(Coords(0,0)) { acc, char -> toCoords(acc, char) }.toSet().size
    }

    override fun part2(input: List<String>): Any? {
        val (odd, even) = input[0].withIndex().partition { it.index % 2 == 0 }
        val start = Coords(0,0)
        val santa = odd.runningFold(start) { acc, char -> toCoords(acc, char.value) }.toSet()
        val robo = even.runningFold(start) { acc, char -> toCoords(acc, char.value) }.toSet()
        return (santa+robo).size
    }
}

fun main() = Day.solve(Day03(), 4, 3)
