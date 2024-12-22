package days

import it.senape.aoc.utils.Day


class Day22 : Day(2024, 22) {
    override fun part1(input: List<String>): Any {
        val inputs = input.map { it.toLong() }
        val secrets = mutableSetOf<Long>()
        inputs.forEach { num ->
            var secret = num
            for (i in 1..2000) {
                secret = mixAndPrune(secret * 64, secret)
                secret = mixAndPrune(secret / 32, secret)
                secret = mixAndPrune(secret * 2048, secret)

            }
            secrets.add(secret)
        }
        return secrets.sum()
    }

    override fun part2(input: List<String>): Any {
        return 0
    }

    fun mixAndPrune(num: Long, secret: Long): Long {
        return (num xor secret) % 16777216
    }
}

fun main() = Day.solve(Day22(), 37327623L, 0)