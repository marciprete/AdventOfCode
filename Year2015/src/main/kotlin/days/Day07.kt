import it.senape.aoc.utils.Day

class Day07 : Day(2015, 7) {


    val VAR_PATTERN = "[a-z]".toRegex()
    fun evaluateNodes(board: Map<String, String>) : Map<String, String> {

        return board.mapValues { (_, operator) ->
            when {
                operator.contains(" AND ") -> {
                    val (a, b) = operator.split(" AND ")
                    a.toInt() and b.toInt()
                }

                operator.contains(" OR ") -> {
                    val (a, b) = operator.split(" OR ")
                    a.toInt() or b.toInt()
                }

                operator.contains(" LSHIFT ") -> {
                    val (a, b) = operator.split(" LSHIFT ")
                    a.toInt() shl b.toInt()
                }

                operator.contains(" RSHIFT ") -> {
                    val (a, b) = operator.split(" RSHIFT ")
                    a.toInt() shr b.toInt()
                }

                operator.contains("NOT ") -> {
                    val (a) = listOf(operator.substring(4))
                    a.toInt().inv()
                }

                else -> operator.toInt()
            }
        }.mapValues { (0xFFFF and it.value).toString() }
    }

    fun getSignal(board: Map<String, String>, target: String) : String? {
        //get all the initial values
        val processed = board.filterValues { VAR_PATTERN !in it }.toMutableMap()

        while (target !in processed) {
            //now we remove the initial values from the board, and get only the ones being processed
            var currentBoard = board.filterKeys { it !in processed.keys }

            //update current board values with the numeric ones in the processed map
            currentBoard = currentBoard.mapValues { (_, v) ->
                processed.toList().fold(v) { acc, value ->
                    acc.replace("\\b${value.first}\\b".toRegex(), value.second)
                }
            }

            //update the processed values evaluating only the full patterns
            processed.putAll(evaluateNodes(currentBoard.filterValues { VAR_PATTERN !in it }))
        }
        return processed[target]
    }
    override fun part1(input: List<String>): Any? {
        val target = "a"
        val board = input.map { line ->
            val (operator, wire) = line.split(" -> ")
            wire to operator
        }.toMap()
        return getSignal(board, target)
    }


    override fun part2(input: List<String>): Any? {
        val board = mutableMapOf<String, String>()
        board.putAll(input.map { line ->
            val (operator, wire) = line.split(" -> ")
            wire to operator
        })
        board["b"]="3176"
        val signal = getSignal(board, "a")
        return signal
    }
}


fun main() = Day.solve(Day07(), "65412", "65412")
