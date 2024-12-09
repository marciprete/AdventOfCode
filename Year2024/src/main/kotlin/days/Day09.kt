package days

import it.senape.aoc.utils.Day
import kotlin.math.min


class Day09 : Day(2024, 9) {
    override fun part1(input: List<String>): Any {
        val files = mutableListOf<Int>()
        val spaces = mutableListOf<Int>()
        val result = mutableListOf<Long>()
        input[0].forEachIndexed { idx, digit ->
            when(idx % 2 != 0) {
                true -> spaces.add(digit.digitToInt())
                false -> files.add(digit.digitToInt())
            }
        }

        var i = files.lastIndex;
        var j = 0
        append(result, j, files[j])
        while (i -j != 0) {
            val diff = files[i] - spaces[j]
            if (diff <= 0) {
                append(result, i, spaces[j]+diff)
                spaces[j] -= files[i]
                i--
            } else {
                append(result, i, spaces[j])
                files[i] -= spaces[j]
                spaces[j] = 0
                j++
                if(j<spaces.size) {
                    append(result, j, files[j])
                }
            }
        }
        return result.foldIndexed(0L){idx, acc, value -> acc + (value * idx)}

    }

    fun append(list: MutableList<Long>, value: Int, times: Int) {
        for (count in 0 until times) {
            list.add(value.toLong())
        }
    }

    override fun part2(input: List<String>): Any {
        val files = mutableListOf<Int>()
        val spaces = mutableListOf<Int>()
        val result = mutableListOf<Long>()
        input[0].forEachIndexed { idx, digit ->
            when(idx % 2 != 0) {
                true -> spaces.add(digit.digitToInt())
                false -> files.add(digit.digitToInt())
            }
        }
        var i = files.lastIndex;
        var j = 0
        append(result, j, files[j])
        while (j < spaces.size) {
            val diff = spaces[j] - files[i]
            if (files[i] != 0 && diff >= 0) {
                append(result, i, spaces[j] - diff)
                spaces[j] -= files[i]
                files[i] = 0
                i--
                if(spaces[j] == 0) {
                    j++
                    if(result.contains(i.toLong())) {
                        append(result, 0, 1)
                    } else {
                        append(result, i, spaces[j])
                    }
                    i = files.lastIndex
                }
            } else {
                if (i>j) {
                    i--
                } else {
                    append(result, 0, spaces[j])
                    j++
                    append(result, j, files[j])
                    i=files.lastIndex
                }
            }
        }
        return 0
    }
}

fun main() = Day.solve(Day09(), 1928L, 2858L)