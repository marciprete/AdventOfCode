package days

import it.senape.aoc.utils.Day


class Day05 : Day(2024, 5) {
    override fun part1(input: List<String>): Any {
        var parsingRules = true
        val rules = mutableMapOf<Int, MutableList<Int>>()
        val pages = mutableListOf<MutableList<Int>>()
        input.forEach { line ->
            if (line.isEmpty()) {
                parsingRules = false
            } else {
                when (parsingRules) {
                    true -> {
                        val nums = line.split("|").map { it.toInt() }.toMutableList()
                        if (rules.get(nums[0]) == null) {
                            rules[nums[0]] = mutableListOf()
                        }
                        rules[nums[0]]!!.add(nums[1])
                    }
                    false -> {
                        val split = line.split(",").map { it.toInt() }.toMutableList()
                        pages.addAll(mutableListOf(split))
                    }
                }
            }
        }

        val validPages = getValidPages(pages, rules)
        var sum = validPages.map { it[it.lastIndex/2] }.sum()

        println(sum)
        return sum
    }

    private fun isHigher(comparing: Int, to: Int, rules: MutableMap<Int, MutableList<Int>>): Boolean {
        if(rules[comparing] != null) {
            return !rules[comparing]!!.contains(to)
        } else if(rules[to] != null) {
            return rules[to]!!.contains(comparing)
        }
        return false
    }

    private fun getValidPages(pages: MutableList<MutableList<Int>>, rules: MutableMap<Int, MutableList<Int>>): MutableList<MutableList<Int>> {
        val subList = mutableListOf<MutableList<Int>>()
        pages.forEach { page ->
            var valid=true
            for (i in 0..page.lastIndex - 1) {
                for (j in i + 1..page.lastIndex) {
                    if (isHigher(page[i], page[j], rules)) {
                        valid = false
                    }
                }
            }
            if (valid) {
                subList.add(page)
            }
        }
        return subList
    }

    override fun part2(input: List<String>): Any {
        var parsingRules = true
        val rules = mutableMapOf<Int, MutableList<Int>>()
        val pages = mutableListOf<MutableList<Int>>()
        input.forEach { line ->
            if (line.isEmpty()) {
                parsingRules = false
            } else {
                when (parsingRules) {
                    true -> {
                        val nums = line.split("|").map { it.toInt() }.toMutableList()
                        if (rules.get(nums[0]) == null) {
                            rules[nums[0]] = mutableListOf()
                        }
                        rules[nums[0]]!!.add(nums[1])
                    }
                    false -> {
                        val split = line.split(",").map { it.toInt() }.toMutableList()
                        pages.addAll(mutableListOf(split))
                    }
                }
            }
        }
        val validPages = getValidPages(pages, rules)
        pages.removeAll(validPages)
        sort(pages, rules)
        var sum = pages.map { it[it.lastIndex/2] }.sum()
        return sum
    }

    private fun sort(pages: MutableList<MutableList<Int>>, rules: MutableMap<Int, MutableList<Int>>) {
        pages.forEach{ page ->
            if(page.isNotEmpty()) {
                bubbleSort(page, rules)
            }
        }
    }

    private fun bubbleSort(page: MutableList<Int>, rules: MutableMap<Int, MutableList<Int>>) {
        for (i in 0..page.lastIndex-1) {
            for (j in i + 1..page.lastIndex) {
                if(isHigher(page[j], page[i], rules)) {
                    val a = page[i]
                    val b = page[j]
                    page[i] = b
                    page[j] = a
                }
            }
        }

    }


}

fun main() = Day.solve(Day05(), 143, 123)