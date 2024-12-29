package days

import data.MultiNode
import data.Coords
import data.Matrix
import data.Move

import it.senape.aoc.utils.Day


class Day12 : Day(2024, 12) {
    override fun part1(input: List<String>): Any {
        val matrix = Matrix.buildCharMatrix(input)
        val plants = matrix.flatten().toSet().toList()
        val prices = mutableListOf<Int>()

        val history = mutableSetOf<Coords>()

        plants.forEach { plant ->
            val allPlantsCoords = matrix.findAll(plant).toMutableList()
            allPlantsCoords.forEach { coord ->
                if (!history.contains(coord)) {
                    val region = traverse(coord, plant, matrix, history)
                    val area = region!!.keys().size
                    val perimeter = perimeter(region)
                    prices.add(area * perimeter)
                }
            }
        }
        return prices.sum()
    }

    override fun part2(input: List<String>): Any {
        val matrix = Matrix.buildCharMatrix(input)
        val plants = matrix.flatten().toSet().toList()
        val prices = mutableListOf<Int>()
        val history = mutableSetOf<Coords>()
        plants.forEach { plant ->
            val allPlantsCoords = matrix.findAll(plant).toMutableList()
            allPlantsCoords.forEach { coord ->
                if (!history.contains(coord)) {
                    val region = traverse(coord, plant, matrix, history)
                    val area = region!!.keys().size
                    val perimeter = fence(region, matrix)
                    prices.add(area * perimeter)
                }
            }
        }
        return prices.sum()
    }

    private fun perimeter(region: MultiNode<Coords>): Int {
        val plants: List<Coords> = region.keys()
        var perimeter = 0
        plants.forEach { plant ->
            Move.straightValues().forEach { move ->
                val fence = plant.move(move.to)
                if (!plants.contains(fence)) perimeter++
            }
        }
        return perimeter
    }

    fun getCorners(currentPosition: Coords, region: MultiNode<Coords>, matrix: Matrix<Char>): Int {
        var corners = 0
        val keys = region.keys()
        val current = matrix.getAt(keys[keys.indexOf(currentPosition)])

        val neighbors = Move.entries.associateWith { move ->
            keys.indexOf(currentPosition.move(move.to)).takeIf { it != -1 }?.let { matrix.getAt(keys[it]) }
        }

        Move.cornerValues().forEach { direction ->
            val mirror = neighbors[direction]?.let {
                keys.indexOf(currentPosition.move(direction.to)).takeIf { it != -1 }?.let { matrix.getAt(keys[it]) }
            }

            when (direction) {
                Move.UP_LEFT -> {
                    if ((neighbors[Move.LEFT] == neighbors[Move.UP] && mirror != current) ||
                        (neighbors[Move.LEFT] == neighbors[Move.UP] && neighbors[Move.LEFT] != mirror)) {
                        corners++
                    }
                }
                Move.UP_RIGHT -> {
                    if ((neighbors[Move.UP] == neighbors[Move.RIGHT] && mirror != current) ||
                        (neighbors[Move.UP] == neighbors[Move.RIGHT] && neighbors[Move.UP] != mirror)) {
                        corners++
                    }
                }
                Move.DOWN_LEFT -> {
                    if ((neighbors[Move.LEFT] == neighbors[Move.DOWN] && mirror != current) ||
                        (neighbors[Move.LEFT] == neighbors[Move.DOWN] && neighbors[Move.LEFT] != mirror)) {
                        corners++
                    }
                }
                Move.DOWN_RIGHT -> {
                    if ((neighbors[Move.DOWN] == neighbors[Move.RIGHT] && mirror != current) ||
                        (neighbors[Move.DOWN] == neighbors[Move.RIGHT] && neighbors[Move.DOWN] != mirror)) {
                        corners++
                    }
                }
                else -> {
                    // Do nothing
                }
            }
        }

        return corners
    }

    private fun fence(region: MultiNode<Coords>, matrix: Matrix<Char>): Int {
        var perimeter = 0
        val plants: List<Coords> = region.keys()
        val sorted = plants.sortedWith(compareBy({ it.y }, { it.x })).groupBy { it.y }
        sorted.forEach { k, v ->
            v.forEach { pos ->
                var count = 0
                val corners = getCorners(pos, region, matrix)
                count += corners
                perimeter += count
            }
        }
        return perimeter
    }

    /**
     * build a MultiNode tree with the all the plants of the same type
     */
    private fun traverse(
        point: Coords,
        current: Char,
        matrix: Matrix<Char>,
        history: MutableSet<Coords>,
    ): MultiNode<Coords>? {
        if (!history.contains(point) && matrix.getAt(point) == current) {
            val node = MultiNode(point)
            history.add(point)
            val steps = listOf(
                node.key.move(Move.LEFT.to),
                node.key.move(Move.RIGHT.to),
                node.key.move(Move.DOWN.to),
                node.key.move(Move.UP.to)
            )
            steps.forEach { step ->
                if (matrix.getAt(step) == current) {
                    traverse(step, current, matrix, history)?.let { node.add(it) }
                }
            }
            return node
        }
        return null
    }
}

fun main() = Day.solve(Day12(), 1930, 1206)