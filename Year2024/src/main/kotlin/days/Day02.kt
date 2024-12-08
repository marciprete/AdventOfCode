package it.senape.aoc.day01

import it.senape.aoc.utils.Day


class Day02 : Day(2024, 2) {
    override fun part1(input: List<String>): Any {
        var counter = 0
        input.forEach { line ->
            val report = line.split(" ").map { it.toInt() }
            val increasing = report[1] - report[0] > 0
            if (safetyCheck(report, increasing)) counter++
        }
        return counter
    }

    override fun part2(input: List<String>): Any {
        println("--- part 2 ---")
        var counter = 0
        input.forEach { line ->
            val report = line.split(" ").map { it.toInt() }
            val increasing = report[1] - report[0] > 0
            if (safetyCheck(report, increasing)) {
                counter++
            } else {
                var isNowSafe = false;
                for (i in 0..report.lastIndex) {
                    val reductio = mutableListOf<Int>()
                    reductio.addAll(report)
                    reductio.removeAt(i)
                    isNowSafe = safetyCheck(reductio, reductio[1]-reductio[0]>0)
                    if (isNowSafe) {
                        counter++
                        break
                    } else {
                        println(reductio)
                    }
                }
                println(isNowSafe)
            }
        }
        return counter
    }

    fun safetyCheck(report: List<Int>, increasing: Boolean): Boolean {
        var isSafe = true;
        report.windowed(2).forEach { pair ->
            if (pair[1] - pair[0] > 0 != increasing) {
                isSafe = false
                return@forEach
            } else if (
                pair[1] - pair[0] == 0 || Math.abs(pair[1] - pair[0]) > 3) {
                isSafe = false
            }


        }
        return isSafe
    }
}

fun main() = Day.solve(Day02(), 2, 4)