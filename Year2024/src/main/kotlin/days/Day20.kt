package days

import data.Coords
import data.Maze
import data.Maze.Companion.SPACE
import data.Move
import it.senape.aoc.utils.Day


class Day20 : Day(2024, 20) {
    override fun part1(input: List<String>): Any {
        val maze = Maze(input)
        val start = maze.findFirst('S')!!
        val end = maze.findFirst('E')!!
        println(maze.breadthFirst(start)[end])
        val io = maze.breadthFirst(start)[end]?.toInt()
        val mazes = cheater(maze)
        println(mazes.size)
        val resi = mazes.map { clone ->
            84 - clone.breadthFirst(start)[end]!!.toInt()
        }.groupingBy { it }.eachCount()
        println(resi)
        return 0
    }

    override fun part2(input: List<String>): Any {
        return 0
    }

    fun cheater(maze: Maze): List<Maze> {

        val mazes = mutableListOf<Maze>()
        val cheats = mutableSetOf<Cheat>()
        var i = 0
//        val walls = maze.findAll(WALL).toMutableList()
//        walls.removeIf { it.x == 0 || it.y == 0 || it.x == maze.lastIndex || it.y == maze.lastIndex }
//        walls.forEach { startPosition ->
//            Move.straightValues().forEach { direction ->
//                val endPosition = startPosition.move(direction.to)
//                val control = endPosition.move(direction.to)
//                val atFirst = maze.getAt(endPosition)
//                val atSecond = maze.getAt(control)
//                if (atFirst == SPACE) {
//                    i++
//                    cheats.add(Cheat(startPosition, startPosition))
//                } else if (atFirst == WALL && atSecond == SPACE) {
//                    i++
//                    cheats.add(Cheat(startPosition, endPosition))
//                }
//            }
//        }
        val spaces = maze.findAll(SPACE)

        spaces.forEach { space ->
            maze.getSiblings(space).forEach { startPosition ->
                if (maze.getAt(startPosition) != SPACE) {
                    val endPosition = space.getRelativeDirectionTo(startPosition)
                    if (endPosition != Move.DOWN_RIGHT && maze.getAt(endPosition.to) == SPACE) {
                        i++
                        cheats.add(Cheat(startPosition, startPosition))
                    } else {
                        val control = startPosition.getRelativeDirectionTo(endPosition.to).to
                        if (maze.getAt(control) == SPACE) {
                            i++
                            cheats.add(Cheat(startPosition, endPosition.to))
                        }
                    }
                }
            }
        }
        println("Cheats sono ${cheats.size} su $i")
        cheats.forEach { cheat ->
            val clone = maze.clone()
            clone.putAt(cheat.a, SPACE)
            clone.putAt(cheat.b, SPACE)
            mazes.add(clone)
        }


        return mazes
    }

}

data class Cheat(val a: Coords, val b: Coords) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cheat
        return (this.a == other.a && this.b == other.b) ||
                (this.a == other.b && this.b == other.a)
    }

    override fun hashCode(): Int {
        return a.hashCode() + b.hashCode()
    }
}

fun main() = Day.solve(Day20(), 84, 0)