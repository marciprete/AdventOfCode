package days

import data.AdjacencyListGraph
import it.senape.aoc.utils.Day

class LanParty() : ArrayList<String>() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LanParty
        return this.containsAll(other)
    }

    override fun hashCode(): Int {
        if (this.isEmpty()) return 9
        return this.sumOf { it.hashCode() }
    }
}

class Day23 : Day(2024, 23) {
    override fun part1(input: List<String>): Any {
        val graph = AdjacencyListGraph<String>()
        input.forEach { pcs ->
            val split = pcs.split("-")
            graph.addUndirectedEdge(graph.createVertex(split[0]), graph.createVertex(split[1]))
        }
        val shuffled = mutableSetOf<LanParty>()
        graph.getVertices().forEach { start ->
            graph.getEdges(start).forEach { middle ->
                graph.getEdges(middle.to)
                    .forEach { edge ->
                        if (middle.to != start && edge.to != start && graph.getEdges(edge.to).map { it.to }
                                .contains(middle.from)) {
                            val lanParty = LanParty()
                            lanParty.addAll(listOf(start.data, middle.to.data, edge.to.data))
                            shuffled.add(lanParty)
                        }
                    }
            }
        }
        val filterByT = shuffled.filter {
            it.any { it.startsWith("t") }
        }
        return filterByT.size
    }

    override fun part2(input: List<String>): Any {
        val graph = AdjacencyListGraph<String>()
        input.forEach { pcs ->
            val split = pcs.split("-")
            graph.addUndirectedEdge(graph.createVertex(split[0]), graph.createVertex(split[1]))
        }
        val cliques = graph.getCliques()
        return cliques.maxBy { it.size }.map{it.data}.sorted().joinToString(",")
    }

}

fun main() = Day.solve(Day23(), 7, "co,de,ka,ta")