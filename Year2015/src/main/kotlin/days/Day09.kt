import data.AdjacencyListGraph
import it.senape.aoc.utils.Day

typealias Stars = AdjacencyListGraph<String>

class Day09(year: Int, day: Int) : Day(year, day) {


    override fun part1(input: List<String>): Any? {
        return prepare(input).nearestNeighbour().second
    }

    override fun part2(input: List<String>): Any? {
        return prepare(input).farthestNeighbour().second
    }

    private fun prepare(input: List<String>): Stars {
        val stars = Stars()
        input.map { line ->
            val matchGroup = "(\\w+)\\sto\\s(\\w+)\\s=\\s([0-9]+)".toRegex().find(line)
            if (matchGroup != null) {
                val (source, dest, weight) = matchGroup.destructured
                val sourceNode = stars.createVertex(source)
                val destNode = stars.createVertex(dest)
                stars.addUndirectedEdge(sourceNode, destNode, weight.toDoubleOrNull())
            }
        }
        return stars
    }
}

fun main() = Day.solve(Day09(2015, 9), 605.0, 982.0)
