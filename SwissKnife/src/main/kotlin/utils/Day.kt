package it.senape.aoc.utils

import addLeadingZero
import readInput
import kotlin.system.measureTimeMillis

abstract class Day(private val year: Int, private val day: Int) {
    val input: List<String> = readInput(year, addLeadingZero(day))
    val testInput = readInput(year, addLeadingZero(day)+"_test")

    open fun part1(input: List<String>) : Any? = null
    open fun part2(input: List<String>) : Any? = null

    companion object {
        fun solve(day: Day, part1Test : Any , part2Test : Any) {
            with(day) {
                println("Solving day ${day.day} of $year")
                check(day.part1(testInput) == part1Test)
                measureTimeMillis {
                    println("Part 1: ${day.part1(input) ?: "Unresolved"}")
                }.run {
                    println("Time: $this ms")
                }
                check(day.part2(testInput) == part2Test)
                measureTimeMillis {
                    println("Part 2: ${day.part2(input) ?: "Unresolved"}")
                }.run {
                    println("Time: $this ms")
                }

            }
        }
    }
}