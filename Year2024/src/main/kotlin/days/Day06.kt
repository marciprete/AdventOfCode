package days

import data.Coords
import data.Matrix
import data.Move
import it.senape.aoc.utils.Day


class Day06 : Day(2024, 6) {
    override fun part1(input: List<String>): Any {
        val matrix = buildFromInput(input)
        val positions = getVisitedPositions(matrix)
        return positions.first.size
    }

    override fun part2(input: List<String>): Any {
        val matrix = buildFromInput(input)
        val positions = getVisitedPositions(matrix).first.toMutableList()
        val obstacles = mutableSetOf<Coords>()
        val startPosition = matrix.findFirst('^')
        positions.remove(startPosition!!)
        val elements = matrix.findAll('#')
        positions.removeAll(elements)
        positions.forEach { obstacle ->
            val replaced = matrix.replaceAt(obstacle, '#')
            val result = getVisitedPositions(replaced, false)
            if (result.second) {
                obstacles.add(obstacle)
            }
        }
        //1810 troppo alto
        //268  troppo basso
        return obstacles.size
    }

    fun getVisitedPositions(matrix: Matrix<Char>, includeLast : Boolean = true ): Pair<Set<Coords>, Boolean> {
        val positions = mutableSetOf<Coords>()
        val startPosition = matrix.findFirst('^')
        var isLoopOrNull = true
        if (startPosition != null) {
            isLoopOrNull = false
            if(includeLast) {
                positions.add(startPosition)
            }
            var next = startPosition
            var starting = Move.LEFT
            var direction = turnToRight(starting)

            var trunk = walk(matrix, next, direction)
            val loopChecker = mutableSetOf<Coords>()
            var moveCondition = true
            while (moveCondition) {
                moveCondition = trunk.third != null //&& isLoop
                direction = turnToRight(direction)

                positions.addAll(trunk.second)
                loopChecker.add(trunk.first)
                trunk = walk(matrix, trunk.first, direction)
                if(loopChecker.contains(trunk.first) && trunk.second.isNotEmpty()) {
                    isLoopOrNull = true
                    return Pair(setOf(trunk.first), isLoopOrNull)
                }
            }
            //add last step
            if(includeLast) {
                positions.addAll(trunk.second)
            }
        }
        return Pair(positions, isLoopOrNull)
    }

    fun buildFromInput(input: List<String>): Matrix<Char> {
        val matrix = Matrix<Char>()
        input.forEachIndexed { y, line ->
            matrix.add(ArrayList())
            line.forEachIndexed { _, char ->
                matrix[y].add(char)
            }
        }
        return matrix
    }

    fun walk(matrix: Matrix<Char>, currentPosition: Coords, direction: Move): Triple<Coords, MutableList<Coords>, Char?> {
        val goBack = matrix.getAt(currentPosition) != '#'
        var next = currentPosition.move(direction.to)
        var nextChar = matrix.getAt(next)
        var steps = mutableListOf<Coords>()

        while (nextChar != null && nextChar != '#') {
            steps.add(next)
            next = next.move(direction.to)
            nextChar = matrix.getAt(next)
        }
        if (goBack || steps.isNotEmpty()) {
            next = next.move(goBack(direction).to)
        }
        return Triple(next, steps, nextChar)
    }

    fun turnToRight(from: Move): Move {
        return when (from) {
            Move.UP -> Move.RIGHT
            Move.RIGHT -> Move.DOWN
            Move.DOWN -> Move.LEFT
            Move.LEFT -> Move.UP
            else -> Move.RIGHT
        }
    }

    fun goBack(from: Move): Move {
        return when (from) {
            Move.UP -> Move.DOWN
            Move.RIGHT -> Move.LEFT
            Move.DOWN -> Move.UP
            Move.LEFT -> Move.RIGHT
            Move.UP_LEFT -> Move.DOWN_RIGHT
            Move.DOWN_LEFT -> Move.UP_RIGHT
            Move.UP_RIGHT -> Move.DOWN_LEFT
            Move.DOWN_RIGHT -> Move.UP_RIGHT

        }
    }

}

fun main() = Day.solve(Day06(), 41, 6)