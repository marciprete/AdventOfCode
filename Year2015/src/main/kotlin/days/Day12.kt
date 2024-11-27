import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import it.senape.aoc.utils.Day


class Day12(year: Int, day: Int) : Day(year, day) {
    val regex = "(-?[\\d]+)".toRegex()
    override fun part1(input: List<String>): Any? {
        val sop = input.map { line ->
             regex.findAll(line).map { it.value.toLong() }.toList()
        }
        println(sop.flatten().sum())
        return sop.flatten().sum()
    }

    override fun part2(input: List<String>): Any? {
        val jsonString = input.reduce { acc, line -> acc + line.trim() }
        val jsonObject = JsonParser.parseString(jsonString).getAsJsonObject()
        return countNumbers(jsonObject)
    }

    private fun countNumbers(value: JsonElement) : Int {
            return 0 + when {
                value.isJsonArray -> value.asJsonArray.sumOf { countNumbers(it) }
                value.isJsonObject -> hasRed(value.asJsonObject)
                else -> if(value.toString().isNumber()) return value.asInt else 0
            }
    }

    private fun hasRed(jsonObject: JsonObject): Int {
        val values = jsonObject.keySet().map{ key -> jsonObject[key]}.toList()
        return if(values.any{ it.toString() == "\"red\""}) 0
        else 0 + jsonObject.keySet().sumOf { key ->
            countNumbers(jsonObject[key])
        }

    }

}

fun main() = Day.solve(Day12(2015, 12), 45L, 28)
