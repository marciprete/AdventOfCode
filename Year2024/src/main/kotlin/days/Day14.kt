package days

import data.Coords
import it.senape.aoc.utils.Day


class Day14 : Day(2024, 14) {

    val regex = "(-?[0-9]+)".toRegex()

    override fun part1(input: List<String>): Any {
        val edge = Coords(101,103)
        val middle = Coords(50,51)

        val robots = input.map { line ->
            val coords = regex.findAll(line).toList().map{ line -> line.groupValues[1].toInt() }
            val p = Coords(coords[0], coords[1])
            val v =Coords(coords[2], coords[3])
            Pair(p, v)
        }
        val positions = robots.map { robot ->
            robot.first.teleport(robot.second, 100, edge)
        }
        val quadrants = mutableListOf(0,0,0,0)
        positions.forEach { position ->
            //sinistra
            if (position.x < middle.x) {
                //sopra
                if (position.y < middle.y) {
                    quadrants[0] += 1
                } else if (position.y > middle.y) {
                    quadrants[1] += 1
                }
            } else if(position.x > middle.x) {
            //destra
                if (position.y < middle.y) {
                    quadrants[2] += 1
                } else if (position.y > middle.y) {
                    quadrants[3] += 1
                }
            }
        }
        return quadrants.fold(1) { acc, quadrant -> acc * quadrant }
    }

    override fun part2(input: List<String>): Any {
        val robots = input.map { line ->
            val coords = regex.findAll(line).toList().map{ line -> line.groupValues[1].toInt() }
            val p = Coords(coords[0], coords[1])
            val v =Coords(coords[2], coords[3])
            Pair(p, v)
        }
        return 0
    }
}

fun main() = Day.solve(Day14(), 21, 0)