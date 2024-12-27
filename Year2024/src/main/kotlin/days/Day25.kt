package days

import data.Maze
import it.senape.aoc.utils.Day


class Day25 : Day(2024, 25) {
    override fun part1(input: List<String>): Any {
        val keys = mutableListOf<Maze>()
        val locks = mutableListOf<Maze>()
        var maze = Maze(5,7)
        input.forEachIndexed { i, line ->
            val y = i % 8
            if (y == 0) {
                if (line.startsWith(".")) keys.add(maze) else locks.add(maze)
                line.forEachIndexed { x, char ->
                    maze[y][x] = char
                }
            }
            if (line.isEmpty()) {
                maze = Maze(5,7)
            } else {
                line.forEachIndexed { x, char ->
                    maze[y][x] = char
                }
            }
        }

        val pins = locks.map { it.findAll(Maze.SPACE)}
        val holes = keys.map { it.findAll(Maze.WALL)}
        var counts = 0
        pins.forEach { pin ->
            holes.forEach { hole ->
                if (pin.containsAll(hole)) counts++
            }
        }
        return counts
    }

    override fun part2(input: List<String>): Any {
        return 0
    }
}

fun main() = Day.solve(Day25(), 3, 0)