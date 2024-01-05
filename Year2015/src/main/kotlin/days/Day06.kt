import it.senape.aoc.utils.Day
import kotlin.math.max

class Day06 : Day(2015, 6) {

    var brightness = false

    override fun part1(input: List<String>): Any? {
        var row = 0
        val lights = mutableListOf<MutableList<Int>>()

        while (row < 1000) {
            lights.add(
                generateSequence { 0 }.take(1000).toMutableList()
            )
            row++
        }

        input.forEach { line ->
            followTheInstruction(line, lights)
        }

        return lights.map { it.sum()}.sum()

    }

    private fun followTheInstruction(line: String, lights: MutableList<MutableList<Int>>) {
        when {
            line.startsWith("turn on") -> switch(line, lights, "on")
            line.startsWith("turn off") -> switch(line, lights, "off")
            line.startsWith("toggle") -> switch(line, lights, "toggle")
        }
    }

    private fun switch(line: String, lights: MutableList<MutableList<Int>>, action: String) {
        val (from, to) = line.split(" through ")
        val (startx, starty) = from.substring(from.lastIndexOf(" ")).trim().split(",")
        val (endx, endy) = to.trim().split(",")
        val xRange = startx.toInt()..endx.toInt()
        val yRange = starty.toInt()..endy.toInt()
        for (y in yRange) {
            for (x in xRange) {
                when (action) {
                    "on" -> lights[x][y] = if(brightness) 1+lights[x][y] else 1
                    "off" -> lights[x][y] = if(brightness) max(0, lights[x][y]-1) else 0
                    "toggle" -> lights[x][y] = if (brightness) 2+lights[x][y] else 1 - lights[x][y]
                }
            }
        }
    }

    override fun part2(input: List<String>): Any? {
        brightness = true
        return part1(input)
    }
}

fun main() = Day.solve(Day06(), 998996, 1001996)
