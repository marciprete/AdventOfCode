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
        return sum
    }

    override fun part2(input: List<String>): Any {
        val secrets = mutableSetOf<Long>()
        val listOfStrings = mutableListOf<Map<String, Int>>()
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
            val changes = oneDigits.windowed(2).map { it[1] - it[0] }
            val changeBananas = mutableMapOf<String, Int>()
            changes.windowed(4).forEachIndexed { idx, change ->
                val key = change.joinToString("")
                if (changeBananas[key] == null) {
                    changeBananas[key] = oneDigits[idx + 4]
                }
            }
            listOfStrings.add(changeBananas)
            secrets.add(secret)
        }
        val  maxSellableBananas = mutableMapOf<String, Int>()
        listOfStrings.forEach { map ->
            map.keys.forEach { string ->
                maxSellableBananas[string] = (maxSellableBananas[string] ?: 0) + (map[string] ?: 0)
            }
        }

        return maxSellableBananas.values.max().toLong()
    }

    fun mixAndPrune(num: Long, secret: Long): Long {
        return (num xor secret) % 16777216
    }
}

fun main() = Day.solve(Day22(), 37990510L, 23L)