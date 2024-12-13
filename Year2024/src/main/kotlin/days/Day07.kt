package days

import data.MultiNode
import data.BinaryNode
import it.senape.aoc.utils.Day


class Day07 : Day(2024, 7) {

    override fun part1(input: List<String>): Any {
        val equations = parse(input)
        val valid = getValidResults(equations)
        val sum = valid.sum()
        println(sum)
        return sum
    }

    override fun part2(input: List<String>): Any {
        val equations = parse(input)
        val valid = mutableListOf<Long>()
        equations.forEach { equation ->
            val node = MultiNode(equation.second[0])
            toMultiTree(node, equation.second.subList(1, equation.second.size))
            if (node.getAllLeafNodes().map { it.key }.contains(equation.first)) {
                valid.add(equation.first)
            }
        }
        val sum = valid.sum()
        println(sum)
        return sum
    }

    fun parse(input: List<String>): List<Pair<Long, List<Long>>> {
        return input.map { line ->
            val split = line.split(": ")
            val values = split[1].trim().split(" ").map { it.toLong() }
            Pair(split[0].toLong(), values)
        }
    }

    private fun getValidResults(equations: List<Pair<Long, List<Long>>>): MutableList<Long> {
        val valid = mutableListOf<Long>()
        equations.forEach { equation ->
            val binaryNode = BinaryNode(equation.second[0])
            toTree(binaryNode, equation.second.subList(1, equation.second.size))
            if (binaryNode.getAllLeafNodes().map { it.key }.contains(equation.first)) {
                valid.add(equation.first)
            }
        }
        return valid
    }

    private fun toTree(root: BinaryNode<Long>, values: List<Long>) {
        if(values.isNotEmpty()) {
            val sum = root.key + values[0]
            val prod = root.key * values[0]
            root.left = BinaryNode(sum)
            root.right = BinaryNode(prod)
            if (values.size > 1) {
                toTree(root.left!!, values.subList(1, values.size))
                toTree(root.right!!, values.subList(1, values.size))
            }
        }
    }

    private fun toMultiTree(root: MultiNode<Long>, values: List<Long>) {
        if (values.isNotEmpty()) {
            val sum = root.key + values[0]
            val prod = root.key * values[0]
            root.add(MultiNode(sum))
            root.add(MultiNode(prod))
            root.add(MultiNode(concat(root.key, values[0])))
            if (values.size > 1) {
                root.children.forEach { child ->
                    toMultiTree(child, values.subList(1, values.size))
                }
            }
        }
    }


    private fun concat(a: Long, b: Long): Long {
        return (a.toString() + b.toString()).toLong()
    }


}

fun main() = Day.solve(Day07(), 3749L, 11387L)