package it.senape.aoc.day01

import it.senape.aoc.utils.Day

class Day01 : Day(2015, 1) {
    override fun part1(input: List<String>): Any {
        /*
            since the 2 chars are consecutive "(" = ascii 40 and ")" = ascii 41
            the difference can be 1 or 0. (40-40 or 41-40)
            so we add 1 - 0 when the symbol is (
                  and 1 - 2 when the symbol is )
         */
        return input[0].map {
            1 - 2 * it.minus('(')
        }.sum()
    }

    override fun part2(input: List<String>): Any {
        var currentFloor = 0
        return input[0].mapIndexed { idx, character ->
            when (character) {
                ')' -> currentFloor -= 1
                '(' -> currentFloor += 1
            }
            if (currentFloor == -1) return idx+1
        }
    }
}

fun main() = Day.solve(Day01(), -3, 1)