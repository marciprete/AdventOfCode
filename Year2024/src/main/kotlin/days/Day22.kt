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
        val sum = secrets.sum()
        println("Sum: $sum")
        return sum
    }

    override fun part2(input: List<String>): Any {
        val secrets = mutableSetOf<Long>()
        var num = 123L
        val inputs = input.map { it.toLong() }
        inputs.forEach { num ->
            val oneDigits = mutableListOf<Int>()

            var secret = num
            oneDigits.add(num.toString().last().digitToInt())
            for (i in 1..1999) {
                secret = mixAndPrune(secret * 64, secret)
                secret = mixAndPrune(secret / 32, secret)
                secret = mixAndPrune(secret * 2048, secret)
                oneDigits.add(secret.toString().last().digitToInt())
            }
            val strings = oneDigits.windowed(2).map { it[1] - it[0] }
            println("OneDigits: ${strings.windowed(4).map { it.joinToString("") }}")

            println()
            secrets.add(secret)
        }
        return 0
    }

    fun mixAndPrune(num: Long, secret: Long): Long {
        return (num xor secret) % 16777216
    }
}

fun main() = Day.solve(Day22(), 37990510L, 10)