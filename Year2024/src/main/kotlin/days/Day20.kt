package days

import data.Maze
import data.Maze.Companion.WALL
import it.senape.aoc.utils.Day


class Day20 : Day(2024, 20) {
    override fun part1(input: List<String>): Any {
        val maze = Maze(input)
        val start = maze.findFirst('S')!!
        val end = maze.findFirst('E')!!
        println(maze.breadthFirst(start)[end])
        val io = maze.breadthFirst(start)[end]?.toInt()
        return io!!
    }

    override fun part2(input: List<String>): Any {
        return 0
    }

    fun cheater(maze: Maze): List<Maze> {
        val walls = maze.findAll(WALL).toMutableList()
        val innerWalls = walls.removeIf{ it.x == 1 || it.y == 1  || it.x == maze.lastIndex  || it.y == maze.lastIndex }
//        innerWalls.
        val cheated = maze.clone()
//        cheated.
        return listOf()
    }
}

fun main() = Day.solve(Day20(), 84, 0)