package it.senape.aoc.day01

import it.senape.aoc.utils.Day

class Day01 : Day(2015, 1) {
    override fun part1(input: List<String>): Any {
        return input[0].map { 1 - 2 * it.minus('(')}.sum()
    }

    override fun part2(input: List<String>): Any {
        var acc = 0
        return input[0].mapIndexed { idx, c ->
            when (c) {
                ')' -> acc -= 1
                '(' -> acc += 1
            }
            if (acc == -1) return idx+1
        }
    }
}

fun main() = Day.solve(Day01(), -3, 1)