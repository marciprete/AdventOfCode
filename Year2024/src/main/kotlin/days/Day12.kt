package days

import data.MultiNode
import data.Coords
import data.Matrix
import data.Move

import it.senape.aoc.utils.Day
import java.util.ArrayList


class Day12 : Day(2024, 12) {
    override fun part1(input: List<String>): Any {
        val history = mutableSetOf<Coords>()
        val matrix = Matrix<Char>()
        input.forEachIndexed { y, line ->
            matrix.add(ArrayList())
            line.forEachIndexed { x, char ->
                matrix[y].add(char)
            }
        }
        val plants = matrix.flatten().toSet().toList()
        val prices = mutableListOf<Int>()
        plants.forEach { plant ->
            val allPlantsCoords = matrix.findAll(plant).toMutableList()

            allPlantsCoords.forEach { coord ->
                if (!history.contains(coord)) {
                    val region = traverse(coord, plant, matrix, history)
                    val area = region!!.keys().size
                    val perimeter = perimeter(region)
                    prices.add(area * perimeter)

                    path(region, matrix)
//                    val discounted = discountedPerimeter(region, matrix)
//                    println("$plant: $area * $perimeter")
//                    println("$plant: $area * $discounted")
                }
            }
        }
        println(prices)
        return prices.sum()
    }

    override fun part2(input: List<String>): Any {
        return 0
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

    private fun path(region: MultiNode<Coords>, matrix: Matrix<Char>): Int {
        val plants: List<Coords> = region.keys()
        val sorted = plants.sortedWith(compareBy({ it.y }, { it.x })).groupBy { it.y }

        val sortedPlants = sorted.values.flatten()
        if(sortedPlants.size <= 2) { return 4 }
//        val perimeter = plants.filter { isCorner(it, matrix) }
//        println(perimeter)
//        return perimeter.size
        return 0
    }

//    fun isCorner(coords: Coords, matrix: Matrix<Char>): Int {
//        val plant = matrix.getAt(coords)
//        val p1 = matrix.getAt(coords.move(Move.UP.to))
//        val p2 = matrix.getAt(coords.move(Move.RIGHT.to))
//        val p3 = matrix.getAt(coords.move(Move.UP_RIGHT.to))
//        val square = listOf(p1, p2, p3, plant)
//        return when (square.count { it == plant }) {
//            1 -> true
//            3 -> true
//            else -> false
//        }
//
//
////        return when {
////            matrix.getAt(coords.move(Move.UP_RIGHT.to)) != plant ||
////                    matrix.getAt(coords.move(Move.UP_LEFT.to)) != plant ||
////                    matrix.getAt(coords.move(Move.DOWN_LEFT.to)) != plant ||
////                    matrix.getAt(coords.move(Move.DOWN_RIGHT.to)) != plant -> true
////            else -> false
////        }
//    }

    private fun discountedPerimeter(region: MultiNode<Coords>, matrix: Matrix<Char>): Int {
        var perimeter = 0
        val plants: List<Coords> = region.keys()
        val sorted = plants.sortedWith(compareBy({ it.y }, { it.x })).groupBy { it.y }
        sorted.forEach { k, v ->
            var count = 0
            v.forEach { pos ->
                var current = matrix.getAt(pos)!!
                var prevLeft = matrix.getAt(pos.move(Move.LEFT.to)).takeIf { current == it }
                var leftCorner = matrix.getAt(pos.move(Move.UP_LEFT.to)).takeIf { current == it }
                var rightCorner = matrix.getAt(pos.move(Move.UP_RIGHT.to))
                var top = matrix.getAt(pos.move(Move.UP.to)).takeIf { current == it }
                var bottom = matrix.getAt(pos.move(Move.DOWN.to)).takeIf { current == it }
                var bottomLeft = matrix.getAt(pos.move(Move.DOWN_LEFT.to)).takeIf { current == it }
                var next = matrix.getAt(pos.move(Move.RIGHT.to))


                if ((current == prevLeft && top != leftCorner) || (current != prevLeft && top==leftCorner) ) { count++ }
                //left fence
                if ((current != prevLeft && top==leftCorner)) { count++ }
                //right fence
                if (current != next && next != rightCorner) { count++ }
                //bottom fence
                if ((current != prevLeft && bottom == bottomLeft) || (current == prevLeft && bottom != bottomLeft && bottom != null)) { count++ }

            }
            perimeter += count
        }
        println(sorted)
        return perimeter
    }

    private fun traverse(
        point: Coords,
        current: Char,
        matrix: Matrix<Char>,
        history: MutableSet<Coords>
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