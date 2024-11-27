package it.senape.aoc.day01

import it.senape.aoc.utils.Day

class Day14 : Day(2015, 14) {
    override fun part1(input: List<String>): Any {
        val totalTime = 2503
        val reindeers = digest(input)
        println(reindeers)
        val maxValue = reindeers.values.map{  value ->
            calculateDistance(value, totalTime)
        }.max()
        println(maxValue)
        return maxValue
    }

    override fun part2(input: List<String>): Any {
        return 1;
    }

    fun calculateDistance(triple: Triple<Int, Int, Int>, time: Int): Int {
        val interval = triple.second + triple.third
        val division = getDivisionAndRemainder(time, interval)
        val distance = triple.first * triple.second
        return when (division.second > triple.second) {
            true -> (division.first * distance) + distance
            false ->  (division.first * distance) + (triple.first * division.second)
        }

    }

    fun getDivisionAndRemainder(divide: Int, by: Int): Pair<Int, Int> {
        return Pair(divide / by, divide % by)
    }

    fun digest(input: List<String>): Map<String, Triple<Int, Int, Int>> {
        //Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.

        val regex = "^(\\w+)\\s.*?(\\d+)\\s.*?(\\d+)\\s.*?(\\d+)".toRegex()
        val map = mutableMapOf<String, Triple<Int, Int, Int>>()
        input.forEach { line ->
            println(line)
            regex.findAll(line).forEach {
             match ->
                val info = Triple(match.groupValues[2].toInt(), match.groupValues[3].toInt(), match.groupValues[4].toInt())
                map.putIfAbsent(match.groupValues[1], info )
            }
        }
        return map
    }
}

fun main() = Day.solve(Day14(), 2660, 1)