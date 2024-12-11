package days

import it.senape.aoc.utils.Day


class Day09 : Day(2024, 9) {
    override fun part1(input: List<String>): Any {
        val files = mutableListOf<Int>()
        val spaces = mutableListOf<Int>()
        val result = mutableListOf<Long>()
        input[0].forEachIndexed { idx, digit ->
            when (idx % 2 != 0) {
                true -> spaces.add(digit.digitToInt())
                false -> files.add(digit.digitToInt())
            }
        }

        var i = files.lastIndex;
        var j = 0
        append(result, j, files[j])
        while (i - j != 0) {
            val diff = files[i] - spaces[j]
            if (diff <= 0) {
                append(result, i, spaces[j] + diff)
                spaces[j] -= files[i]
                i--
            } else {
                append(result, i, spaces[j])
                files[i] -= spaces[j]
                spaces[j] = 0
                j++
                if (j < spaces.size) {
                    append(result, j, files[j])
                }
            }
        }
        return result.foldIndexed(0L) { idx, acc, value -> acc + (value * idx) }

    }

    private fun append(list: MutableList<Long>, value: Int, times: Int) {
        for (count in 0 until times) {
            list.add(value.toLong())
        }
    }

    override fun part2(input: List<String>): Any {
        val files = mutableListOf<Int>()
        val spaces = mutableListOf<Int>()
        val spacesCopy = mutableListOf<Int>()
        val result = mutableListOf<Long>()
        var curr = 0
        input[0].forEachIndexed { idx, digit ->
            val n = digit.digitToInt()
            if (idx % 2 == 0) {
                files.add(n)
                append(result, curr, n)
                curr++
            } else {
                append(result, -1, n)
                spaces.add(n)
            }
        }
        spacesCopy.addAll(spaces)
        for (i in files.indices.reversed()) {
            var j = 0
            while (j < i) {
                var diff = spaces[j] - files[i]
                if (diff >= 0) {
                    var index = spacesCopy.take(j).sum() + files.take(j + 1).sum()
                    while(result[index] != -1L) {
                        index++
                    }
                    spaces[j] -= files[i]
                    for (count in index until index + files[i]) {
                        result[count] = i.toLong()
                    }
                    val lastIndexOf = result.lastIndexOf(i.toLong())
                    for (count in lastIndexOf downTo (lastIndexOf - files[i]) + 1) {
                        result[count] = -1L
                    }
                    break
                }
                j++
            }

        }
        result.replaceAll {if (it == -1L ) 0L else it }
        return result.foldIndexed(0L) { idx, acc, value -> acc + (value * idx) }
    }

}

fun main() = Day.solve(Day09(), 1928L, 2858L)