package data

class DataStructures {

    companion object {
        fun <T> getPermutations(input: MutableList<T>): MutableList<MutableList<T>> {
            if (input.size == 1) return mutableListOf(input)
            val perms = mutableListOf<MutableList<T>>()
            val toInsert = input[0]
            for (perm in getPermutations(input.drop(1).toMutableList())) {
                for (i in 0..perm.size) {
                    val newPerm = perm.toMutableList()
                    newPerm.add(i, toInsert)
                    perms.add(newPerm)
                }
            }
            return perms
        }
    }

}


