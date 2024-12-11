package days

import data.MultiNode
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
            val node = Node(equation.second[0])
            toTree(node, equation.second.subList(1, equation.second.size))
            if (node.getAllLeafNodes().map { it.key }.contains(equation.first)) {
                valid.add(equation.first)
            }
        }
        return valid
    }

    private fun toTree(root: Node, values: List<Long>) {
        if(values.isNotEmpty()) {
            val sum = root.key + values[0]
            val prod = root.key * values[0]
            root.left = Node(sum)
            root.right = Node(prod)
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



data class Node(val key: Long, var left: Node? = null, var right: Node? = null) {
    override fun toString(): String {
        return "[$key]\n" +
                "   ->$left\n" +
                "   ->$right\n"
    }

    fun children(): List<Node> {
        val children = mutableListOf<Node>()
        if (left != null) children.add(left!!)
        if (right != null) children.add(right!!)
        return children
    }

    fun getAllLeafNodes(): Set<Node> {
        val leafNodes: MutableSet<Node> = HashSet()
        if (this.left == null && this.right == null) {
            leafNodes.add(this)
        } else {
            for (child in this.children()) {
                leafNodes.addAll(child.getAllLeafNodes())
            }
        }
        return leafNodes
    }
}

fun main() = Day.solve(Day07(), 3749L, 11387L)