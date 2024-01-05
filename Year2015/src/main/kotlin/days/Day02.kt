package it.senape.aoc.day01

import it.senape.aoc.utils.Day

class Day02 : Day(2015, 2) {

    override fun part1(input: List<String>): Any? {
        return input.map { line ->
            val (l,w,h) = line.split("x").map { it.toInt() }
            2*l*w + 2*w*h + 2*l*h + smallestSides(l,w,h).fold(1) {acc, size -> acc*size}
        }.sum()
    }

    private fun smallestSides(l: Int, w: Int, h: Int): List<Int> {
        return listOf(l, w, h).sorted().dropLast(1)
    }

    override fun part2(input: List<String>): Any? {
        return input.map { line ->
            val (l,w,h) = line.split("x").map { it.toInt() }
            2 * smallestSides(l,w,h).sum()  + l*w*h
        }.sum()
    }
}

fun main() = Day.solve(Day02(), 101, 48)
