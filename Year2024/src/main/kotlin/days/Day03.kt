package it.senape.aoc.day01

import it.senape.aoc.utils.Day


class Day03 : Day(2024, 3) {
    val multipliers = "mul\\(([\\d]+),([\\d]+)\\)".toRegex()
    val dosAndDonts = "(?:do\\(\\))(.*?)(?=don't\\(\\)|\$)".toRegex()
    override fun part1(input: List<String>): Any {
        return input.map { line ->
            multipliers.findAll(line).map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }.toList().sum()
        }.toList().sum()

    }

    override fun part2(input: List<String>): Any {
        val stringone = "do()"+input.joinToString("\\n")
        val eval = dosAndDonts.findAll(stringone).map { it.groupValues[0] }.toList()
        return part1(eval)
    }
}

fun main() = Day.solve(Day03(), 161, 48)