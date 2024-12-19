package days

import it.senape.aoc.utils.Day


class Day19 : Day(2024, 19) {
    var cache = mutableMapOf<String, Int>()
    var memo = mutableMapOf<String, MutableSet<MutableSet<String>>>()

    override fun part1(input: List<String>): Any {
        val pattern = input[0].split(", ")
        val sorted = pattern.sortedByDescending { it.length }
//        sorted.forEach { value -> cache[value] = 1 }
        val result = input.takeLast(input.lastIndex - 1).map { string ->
            recheck(sorted, string)
        }
        println(result)
        return result.count { it > 0 }

    }

    override fun part2(input: List<String>): Any {
        return 0
    }

//    fun bicheck(string: String): Int {
//
//        memo[string]?.let { return it.size }
//
//        val keysSnapshot = cache.keys.toMutableList()
//        memo[string] = mutableSetOf()
//
//        val iterator = keysSnapshot.iterator()
//        while (iterator.hasNext()) {
//            val block = iterator.next()
//            val chunks = string.split(block)
//            if (chunks.filter { it.isNotEmpty() }.all { chunk -> bicheck(chunk) > 0 }) {
//                keysSnapshot.removeAll(chunks)
//                memo[string].add
//            }
//        }
//        return memo[string] ?: 0
//
//    }

    fun recheck(pattern: List<String>, head: String): Int {
        val cached = cache[head]
        if (cached == null) {
            cache[head] = 0
            var found = 0
            pattern.forEach { block ->
                val chunks = head.split(block)
                if (chunks.size > 1) {
                    found = if (chunks.all { chunk -> chunk.isEmpty() || recheck(pattern, chunk) > 0 }) 1 else 0
                }
                if (found > 0) {
                    cache[head] = cache[head]!! + 1
                    return found
                }
            }
            cache[head] = found
            return found
        }
        return cached
    }

    fun check(pattern: List<String>, head: String): Boolean {
        var found = pattern.contains(head)
        var i = 1
        if (!found) {
            while (i < head.length) {
                if (!found) {
                    val chunks = head.take(i) to head.takeLast(head.length - i)
                    found = check(pattern, chunks.first) && check(pattern, chunks.second)
                }
                i++
            }
        }
        return found
    }
}

fun main() = Day.solve(Day19(), 6, 0)