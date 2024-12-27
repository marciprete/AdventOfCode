package logic

data class Signal(val name: String, var value: Boolean? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Signal

        return name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    //    infix fun and(other: Signal) =  this.value && other.value
//    infix fun or(other: Signal) = this.value || other.value
//    infix fun xor(other: Signal) = this.value != other.value
}

class Gate(val inputs : Pair<Signal, Signal>, private val op: String, private val outputSignal: Signal) {

    init {
        eval()
    }
    val output: Signal
        get() = calculateOutput()


    private fun calculateOutput() : Signal {
        eval()
        return outputSignal
    }

    fun eval() {
        outputSignal.value = if (inputs.first.value == null || null == inputs.second.value) null else
         when (op) {
            "AND" -> inputs.first.value!! && inputs.second.value!!
            "OR" -> inputs.first.value!! || inputs.second.value!!
            "XOR" -> inputs.first.value != inputs.second.value
            else -> throw IllegalArgumentException("Unsupported operation: $op")
        }
    }


    override fun toString(): String {
        return "Gate(inputs=$inputs, output=$output)"
    }
}