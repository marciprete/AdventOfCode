
import data.Coords
import it.senape.aoc.utils.Day

class Day03 : Day(2015, 3) {


    private fun moveToCharAndGetPosition(origin: Coords, char: Char) : Coords {
        return when (char) {
            '^' -> Coords(origin.x, origin.y+1)
            'v' -> Coords(origin.x, origin.y-1)
            '>' -> Coords(origin.x+1, origin.y)
            '<' -> Coords(origin.x-1, origin.y)
            else -> error("Unexpected")
        }
    }
    override fun part1(input: List<String>): Any? {
        val origin = Coords.fromString("0,0");

        return input[0].runningFold(origin) { currentPosition, directionChar -> currentPosition.moveToCharAndGetPosition(directionChar) }.toSet().size
    }

    override fun part2(input: List<String>): Any? {
        val (odd, even) = input[0].withIndex().partition { it.index % 2 == 0 }
        val start = Coords(0,0)
        val santa = odd.runningFold(start) { currentPosition, char -> currentPosition.moveToCharAndGetPosition(char.value) }.toSet()
        val robo = even.runningFold(start) { currentPosition, char -> currentPosition.moveToCharAndGetPosition(char.value) }.toSet()
        return (santa+robo).size
    }
}

fun main() = Day.solve(Day03(), 4, 3)
