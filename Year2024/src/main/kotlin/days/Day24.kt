package days

import it.senape.aoc.utils.Day
import logic.Gate
import logic.Signal


class Day24 : Day(2024, 24) {
    val initRegexp = "([a-z0-9]{3}):\\s([01])".toRegex()
    val boardsRegexp = "([a-z0-9]{3})\\s([A-Z]+)\\s([a-z0-9]{3})\\s->\\s([a-z0-9]{3})".toRegex()
    override fun part1(input: List<String>): Any {
        val initialStates = mutableMapOf<String, Signal>()
        val gates = mutableListOf<Gate>()
        var half = true
        input.forEach { line ->
            if (line.isEmpty()) half = false
            if (half) {
                initRegexp.findAll(line).forEach {
                    initialStates[it.groupValues[1]] = Signal(it.groupValues[1],  it.groupValues[2] == "1")
                }
            } else {
                boardsRegexp.findAll(line).forEach {
                    val input1 = initialStates[it.groupValues[1]] ?: Signal(it.groupValues[1])
                    val op = it.groupValues[2]
                    val input2 = initialStates[it.groupValues[3]] ?: Signal(it.groupValues[3])
                    val out = Signal(it.groupValues[4])
                    initialStates[out.name] = out
                    gates.add(Gate(Pair(input1, input2), op, out))
                    gates.filter { it.inputs.toList().contains(out)}.forEach {
                        if (it.inputs.first.name == out.name) {
                            it.inputs.first.value = out.value
                        } else {
                            it.inputs.second.value = out.value
                        }
                    }
                    gates.forEach { it.eval() }
                }
            }
        }
        gates.map { it.output }.sortedBy { it.name }.forEach {
            println("$it")
        }
        return 0
    }

    override fun part2(input: List<String>): Any {
        return 0
    }
}

fun main() = Day.solve(Day24(), 2024, 0)