package it.senape.aoc.day01

import it.senape.aoc.utils.Day


class Day01 : Day(2024, 1) {
    override fun part1(input: List<String>): Any {
        val first = mutableListOf<Int>()
        val last = mutableListOf<Int>()

        input.map { line ->
            val (left, right) = line.split("   ")
            first.add(left.toInt())
            last.add(right.toInt())
        }
        first.sort()
        last.sort()
        var sum = 0
        for (i in 0..first.lastIndex) {
            sum += Math.abs(first[i]-last[i])
        }
        return sum
    }

    override fun part2(input: List<String>): Any {
        val list = mutableListOf<Int>()
        val score = mutableMapOf<Int, Int>()

        input.map { line ->
            val (left, right) = line.split("   ")
            list.add(left.toInt())
            if (score[right.toInt()] == null) { score.put(right.toInt(), 1) } else score.put(right.toInt(), score[right.toInt()]!! + 1)
        }

        var sum = 0
        for (i in 0..list.lastIndex) {
            val n = list[i]
            sum += n * score.computeIfAbsent(n) { _ -> 0}
        }
        println(sum)
        return sum
    }
}

fun main() = Day.solve(Day01(), 11, 31)