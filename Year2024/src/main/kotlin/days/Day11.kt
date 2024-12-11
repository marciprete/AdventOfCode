package days

import it.senape.aoc.utils.Day


class Day11 : Day(2024, 11) {
    var cache = mutableMapOf<String, Long>()

    override fun part1(input: List<String>): Any {
        val line = input[0].split(' ').map(String::toLong)
        var blink = blink(line)
        for (i in 0 until 24) {
            blink = blink(blink)
        }
        return blink.size
    }

    override fun part2(input: List<String>): Any {
        var stones = input[0].split(' ').map(String::toLong)
        val count = stones.map { count(it, 75) }.sum()
        println("#####")
        println(count)
        return 0
    }

    private fun count(stone: Long, steps: Int): Long {
        val cached = cache["${stone}_${steps}"]
        if (cached == null) {
            if (steps == 0) {
                cache["${stone}_${steps}"] = 1
                return 1
            }
            val blink = rule(stone)
            return if (blink.size == 2) {
                val n = count(blink[0], steps - 1) + count(blink[1], steps - 1)
                cache["${stone}_${steps}"] = n
                n
            } else {
               count(blink[0], steps - 1)
            }
        }
        return cached

    }


    private fun blink(line: List<Long>): List<Long> {
        val blinked = mutableListOf<Long>()
        line.forEach { num ->
            blinked.addAll(rule(num))
        }
        return blinked.toList()
    }


    private fun rule(number: Long): List<Long> {
        return when {
            number == 0L -> return listOf(1L)
            number.toString().length % 2 == 0 -> {
                val stringy = number.toString()
                val head = stringy.substring(0, stringy.length / 2)
                val tail = stringy.substring(stringy.length / 2, stringy.length)
                listOf(head.toLong(), tail.toLong())
            }
            else -> listOf(number * 2024)
        }
    }
}

fun main() = Day.solve(Day11(), 55312, 0)