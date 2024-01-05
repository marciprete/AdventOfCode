package it.senape.aoc.utils

import addLeadingZero
import readInput

abstract class Day(year: Int, day: Int) {
    val input: List<String> = readInput(year, addLeadingZero(day))
    val testInput = readInput(year, addLeadingZero(day)+"_test")

    abstract fun part1(input: List<String>) : Any
    abstract fun part2(input: List<String>) : Any
}