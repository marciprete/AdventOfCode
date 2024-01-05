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
        fun solve(day: Day) {
            with(day) {
                println("Solving day ${day.day} of $year")
                measureTimeMillis {
                    println("Part 1: ${day.part1(input) ?: "Unresolved"}")
                }.run {
                    println("Time: $this ms")
                }
                measureTimeMillis {
                    println("Part 2: ${day.part2(input) ?: "Unresolved"}")
                }.run {
                    println("Time: $this ms")
                }

            }
        }
    }
}