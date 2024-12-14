package days

import it.senape.aoc.utils.Day


class Day13 : Day(2024, 13) {
    val incognite = "^Button\\s[AB]:\\sX\\+([\\d]+),\\sY\\+([\\d]+)".toRegex()
    val noti = "^Prize:\\sX=([\\d]+),\\sY=([\\d]+)".toRegex()
    val woah = 10000000000000
    override fun part1(input: List<String>): Any {
        return solve(input)
    }

    override fun part2(input: List<String>): Any {
        return solve(input, true)
    }

    private fun solve(input: List<String>, bias: Boolean = false) : Long {
        val equations = parse(input, bias)
        return equations.chunked(3).mapNotNull { chunk ->
            cramer(chunk)?.let { it.first*3 + it.second }
        }.sum()
    }

    private fun determinant(equations: List<List<Long>>): Long {
        return (equations[0][0] * equations[1][1]) - (equations[0][1] * equations[1][0])
    }

    private fun cramer(equation: List<List<Long>>): Pair<Long, Long>? {
        val det = determinant(equation).toBigDecimal()
        val x1 = equation[0][0].toBigDecimal()
        val x2 = equation[1][0].toBigDecimal()
        val y1 = equation[0][1].toBigDecimal()
        val y2 = equation[1][1].toBigDecimal()
        val n = equation[2][0].toBigDecimal()
        val m = equation[2][1].toBigDecimal()

        val dx = ((n * y2 -  x2 * m) / det)
        val dy = ((x1 * m - n * y1) / det)

        if ( (dx * x1) + (dy*x2) != n ||
                (dx * y1) + (dy*y2) != m
            ) {
            return null
        }
        return Pair(dx.toLong(), dy.toLong())
    }

    fun parse(input: List<String>, bias: Boolean = false): List<List<Long>> {
        val equations = mutableListOf<MutableList<Long>>()
        input.forEach { line ->

            if (line.isNotEmpty()) {
                when {
                    line.startsWith("Button") -> {
                        val inc = mutableListOf<Long>()
                        incognite.findAll(line).forEach {
                            inc.add(it.groupValues[1].toLong())
                            inc.add(it.groupValues[2].toLong())
                        }
                        equations.add(inc)
                    }
                    else -> {
                        val inc = mutableListOf<Long>()
                        noti.findAll(line).forEach {
                            inc.add(it.groupValues[1].toLong())
                            inc.add(it.groupValues[2].toLong())
                            if (bias) {
                                inc[0] += woah
                                inc[1] += woah
                            }
                        }
                        equations.add(inc)
                    }
                }
            }
        }
        return equations
    }
}

fun main() = Day.solve(Day13(), 480L, 875318608908)