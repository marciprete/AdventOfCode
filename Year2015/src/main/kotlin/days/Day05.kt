
import it.senape.aoc.utils.Day

class Day05 : Day(2015, 5) {

    val naughtyStrings = setOf("ab", "cd", "pq", "xy")
    val vowels = setOf('a', 'e', 'i', 'o', 'u')

    fun String.isNice() : Boolean {
        //doesn't contain the naughty pairs
        return (naughtyStrings.all { s ->
            s !in this
        })
        &&
        //has 3 vowels
        vowels.map { v -> this.count { it==v }}.sum() > 2
                &&
        //has a double
        this.windowed(2).any{ it[0]==it[1]}
    }

    fun String.isNicer() : Boolean {
        return this.containsNonOverlappingPair() && this.containsOneLetterSplitter()
    }

    fun String.containsNonOverlappingPair() : Boolean {
        return this.windowed(2).mapIndexed { idx, pair ->
            this.substring(idx+2).contains(pair)
        }.toSet().contains(true)

    }

    fun String.containsOneLetterSplitter() : Boolean {
        return this.windowed(3).any { it[0]==it[2]}
    }

    override fun part1(input: List<String>): Any? {
        return (input.filter { it.isNice() }).size
    }

    override fun part2(input: List<String>): Any? {
        return (input.filter { it.isNicer() }).size
    }
}

fun main() = Day.solve(Day05(), 2, 2)
