package it.senape.aoc.day01

import data.DataStructures
import it.senape.aoc.utils.Day

class Day13 : Day(2015, 13) {
    override fun part1(input: List<String>): Any {
        val matrix: Map<String, MutableMap<String, Int>> = digest(input)
        val people = matrix.keys.toList()

        val permutations = getNMinusOnePermutations(people)
        return getHappinessList(permutations, matrix).max()

    }

    fun getNMinusOnePermutations(people: List<String>): MutableList<MutableList<String>> {
        val sublist = people.subList(1, people.size).toMutableList()
        val permutations = DataStructures.getPermutations(sublist).toMutableList()
        permutations.forEach {
            it.add(people[0])
        }
        return permutations
    }

    override fun part2(input: List<String>): Any {
        val matrix: MutableMap<String, MutableMap<String, Int>> = digest(input)
        val people = matrix.keys.toMutableList()
        matrix.put("me", people.associateWith { 0 }.toMutableMap())
        matrix.forEach { key, mutableMap -> if (key != "me") mutableMap.putIfAbsent("me", 0) }
        people.add("me")
        val permutations =getNMinusOnePermutations(people)
        var optimal = getHappinessList(permutations, matrix)
        return optimal.max()
    }

    private fun getHappinessList(
        permutations: List<MutableList<String>>,
        matrix: Map<String, MutableMap<String, Int>>
    ): List<Int> {
        val optimal = permutations.map { setup ->
            var happiness = 0
            for (i in 0 until setup.size - 1) {
                happiness += matrix[setup[i]]!![setup[i + 1]]!! + matrix[setup[i + 1]]!![setup[i]]!!
            }
            happiness += matrix[setup.last()]!![setup.first()]!! + matrix[setup.first()]!![setup.last()]!!
            happiness
        }.toList()
        return optimal
    }

    private fun digest(input: List<String>): MutableMap<String, MutableMap<String, Int>> {
        //               1                 2          3                                                          4
        val regex =
            "([\\w]+)\\swould\\s([\\w]+)\\s([0-9]+)\\shappiness\\sunits\\sby\\ssitting\\snext\\sto\\s([\\w]+)".toRegex()
        val matrix = mutableMapOf<String, MutableMap<String, Int>>()
        input.forEach { line ->
            regex.findAll(line).forEach { matchResult ->
                val key = matchResult.groups[1]!!.value
                matrix.putIfAbsent(key, mutableMapOf())
                matrix[key]!!.putIfAbsent(
                    matchResult.groups[4]!!.value,
                    getValue(matchResult.groups[2]!!.value, matchResult.groups[3]!!.value.toInt())
                )
            }
        }
        return matrix
    }

    private fun getValue(operation: String, value: Int): Int {
        var multiplier = 1
        if (operation == "lose") multiplier = -1
        return value * multiplier
    }

}


fun main() = Day.solve(Day13(), 330, 286)