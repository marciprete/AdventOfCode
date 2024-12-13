package data

data class MultiNode<T>(var key: T, val children: MutableList<MultiNode<T>> = mutableListOf()) {
    fun add(node: MultiNode<T>) {
        children.add(node)
    }

    fun getAllLeafNodes(): Set<MultiNode<T>> {
        val leafNodes: MutableSet<MultiNode<T>> = HashSet()
        if (this.children.isEmpty()) {
            leafNodes.add(this)
        } else {
            for (child in this.children) {
                leafNodes.addAll(child.getAllLeafNodes())
            }
        }
        return leafNodes
    }

    fun keys(): List<T> {
        val keys: MutableList<T> = ArrayList()
        keys.add(this.key)
        if (children.isNotEmpty()) {
            for (child in children)
                keys.addAll(child.keys())
        }
        return keys
    }

    fun extractPaths(): List<List<T>> {
        val result = mutableListOf<List<T>>()  // List to store all paths

        // Helper function to perform DFS and construct paths
        fun dfs(currentNode: MultiNode<T>, currentPath: MutableList<T>) {
            // Add the current node's key to the path
            currentPath.add(currentNode.key)

            // If it's a leaf node, add the current path to the result
            if (currentNode.children.isEmpty()) {
                result.add(currentPath.toList())  // Add a copy of the current path
            } else {
                // Recur for each child
                for (child in currentNode.children) {
                    dfs(child, currentPath.toMutableList())  // Create a copy for each branch
                }
            }
        }

        // Start DFS from the root node
        dfs(this, mutableListOf())

        return result
    }
}

data class BinaryNode<T>(val key: T, var left: BinaryNode<T>? = null, var right: BinaryNode<T>? = null) {
    override fun toString(): String {
        return "[$key](L[$left], R[$right])"
    }

    fun children(): List<BinaryNode<T>> {
        val children = mutableListOf<BinaryNode<T>>()
        if (left != null) children.add(left!!)
        if (right != null) children.add(right!!)
        return children
    }

    fun getAllLeafNodes(): Set<BinaryNode<T>> {
        val leafBinaryNodes: MutableSet<BinaryNode<T>> = HashSet()
        if (this.left == null && this.right == null) {
            leafBinaryNodes.add(this)
        } else {
            for (child in this.children()) {
                leafBinaryNodes.addAll(child.getAllLeafNodes())
            }
        }
        return leafBinaryNodes
    }

    fun keys(): List<T> {
        val keys: MutableList<T> = ArrayList()
        keys.add(this.key)
        if (left != null) {
            keys.addAll(left!!.keys())
        }
        if (right != null)
            keys.addAll(right!!.keys())
        return keys
    }

}