package data

import java.util.*
import kotlin.math.max
import kotlin.math.min

/**
 *
 * @author Michele Arciprete
 * created on 07/01/2024
 */
interface Graph<T> {
    /**
     * Create a vertex if not exists
     */
    fun createVertex(data: T): Vertex<T>

    /**
     * Returns the list of all the Vertex
     */
    fun getVertices(): List<Vertex<T>>

    fun get(data: T): Vertex<T>?
    fun remove(data: Vertex<T>): Boolean

    fun addDirectedEdge(from: Vertex<T>, to: Vertex<T>, weight: Double? = null)
    fun addUndirectedEdge(from: Vertex<T>, to: Vertex<T>, weight: Double? = null)
    fun getEdges(vertex: Vertex<T>): List<Edge<T>>
    fun getWeightBetweenNodes(from: Vertex<T>, to: Vertex<T>): Double?

    fun add(type: EdgeType, from: Vertex<T>, to: Vertex<T>, weight: Double?)

    fun depthFirst()
    fun breadthFirst(root: Vertex<T>): Map<T, Double>
    fun breadthFirst(root: Vertex<T>, destination: T): Double?
    fun dijkstra(from: Vertex<T>, to: Vertex<T>): Double
    fun nearestNeighbour(): Pair<List<Edge<T>>, Double>
    fun farthestNeighbour(): Pair<List<Edge<T>>, Double>

    fun getNeighbors(vertex: Vertex<T>): List<Vertex<T>>
    fun getCliques(): List<Set<Vertex<T>>>

}

data class Vertex<T>(val index: Int, val data: T) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vertex<*>

        return data == other.data
    }

    override fun hashCode(): Int {
        return data?.hashCode() ?: 0
    }

    override fun toString(): String {
        return data?.toString() ?: "null"
    }
}

data class Edge<T>(val from: Vertex<T>, val to: Vertex<T>, var weight: Double? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Edge<*>

        if (from == other.from && to == other.to && weight == other.weight) return true
        if (to == other.from && from == other.to && weight == other.weight) return true
        return false
    }

    override fun hashCode(): Int {
        val n = from.hashCode()
        val m = to.hashCode()
        return 31 * (n + m) xor weight.hashCode()
    }
    override fun toString(): String {
        return "$from --($weight)--> $to"
    }
}

enum class EdgeType {
    DIRECTED, UNDIRECTED
}

class AdjacencyListGraph<T>: Graph<T> {
    private var adjacencyList: HashMap<Vertex<T>, MutableList<Edge<T>>> = HashMap()

    override fun getVertices(): List<Vertex<T>> {
        return LinkedList(adjacencyList.keys)
    }

    override fun get(data: T): Vertex<T>? {
        return getVertices().find { it.data == data }
    }

    override fun remove(data: Vertex<T>): Boolean {
        adjacencyList.values.forEach { edges ->
            edges.removeIf { it.to == data || it.from == data }
        }
        return adjacencyList.remove(data) != null

    }

    override fun createVertex(data: T): Vertex<T> {
        var vertex = getVertices().find { it.data == data }
        if (vertex == null) {
            vertex = Vertex(adjacencyList.count(), data)
            adjacencyList[vertex] = ArrayList()
        }
        return vertex
    }

    override fun addDirectedEdge(from: Vertex<T>, to: Vertex<T>, weight: Double?) {
        adjacencyList[from]?.add(Edge(from, to, weight))

    }

    override fun addUndirectedEdge(from: Vertex<T>, to: Vertex<T>, weight: Double?) {
        adjacencyList[from]?.add(Edge(from, to, weight))
        adjacencyList[to]?.add(Edge(to, from, weight))
    }

    override fun getEdges(vertex: Vertex<T>): List<Edge<T>> {
        return adjacencyList[vertex] ?: emptyList()
    }

    override fun getWeightBetweenNodes(from: Vertex<T>, to: Vertex<T>): Double? {
        return getEdges(from).firstOrNull { it.to == to }?.weight
    }

    override fun add(type: EdgeType, from: Vertex<T>, to: Vertex<T>, weight: Double?) {
        when (type) {
            EdgeType.DIRECTED -> {
                addDirectedEdge(from, to, weight)
            }

            EdgeType.UNDIRECTED -> {
                addUndirectedEdge(from, to, weight)
            }
        }
    }

    override fun toString(): String {
        var str: StringBuilder = StringBuilder()
        adjacencyList.forEach { (vertex, edges) ->
            str.append("{${vertex.data}} -> $edges\n")
        }
        return str.toString()
    }

    override fun depthFirst() {
        val discoveredMap: MutableMap<Vertex<T>, Boolean> = mutableMapOf()

        getVertices().forEach { vertex ->
            discoveredMap[vertex] = false
            dfs(this, vertex, discoveredMap)
        }
        discoveredMap.forEach(::println)
    }

    private fun dfs(graph: Graph<T>, vertex: Vertex<T>, discoveredMap: MutableMap<Vertex<T>, Boolean>) {
        discoveredMap[vertex] = true
        graph.getEdges(vertex).forEach { edge ->
            if (discoveredMap[edge.to] == false) {
                dfs(graph, edge.to, discoveredMap)
            }
        }
    }

    override fun breadthFirst(root: Vertex<T>, destination: T): Double? {
        return breadthFirst(root)[destination]
    }

    override fun breadthFirst(root: Vertex<T>): Map<T, Double> {
        var queue: Queue<Vertex<T>> = LinkedList()
        var distances: MutableMap<Vertex<T>, Pair<Vertex<T>?, Double>> = mutableMapOf()

        getVertices().forEach { vertex ->
            distances[vertex] = Pair(null, Double.POSITIVE_INFINITY)
        }

        distances[root] = Pair(root, 0.0)
        queue.add(root)

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            adjacencyList[current]?.forEach { edge ->
                if (distances[edge.to]?.second == Double.POSITIVE_INFINITY) {
                    distances[edge.to] = Pair(current, distances[current]!!.second + 1)
                    queue.add(edge.to)
                }
            }
        }
        return distances.map { (edge, p) -> edge.data to p.second }.toMap()
    }

    override fun dijkstra(from: Vertex<T>, to: Vertex<T>): Double {
        var distances: MutableMap<Vertex<T>, Pair<Vertex<T>?, Double>> = mutableMapOf()
        var queue: Queue<Vertex<T>> = LinkedList()

        getVertices().forEach { vertex ->
            //inizializzo tutte le distanze a infinito
            distances[vertex] = Pair(null, Double.POSITIVE_INFINITY)
            //aggiungo alla coda di esecuzione
        }
        queue.add(from)

        //prendo l'origine e setto la distanza a 0
        var current = from
        distances[current] = Pair(null, 0.0)
        var visited: MutableSet<Vertex<T>> = mutableSetOf()

        while (queue.isNotEmpty()) {
            current = queue.poll()
            visited.add(current)
            //per ogni percorso dal corrente calcolo la distanza minore
            adjacencyList[current]?.forEach { edge ->
                //verifico che non sia stato ancora attraversato
                if (!visited.contains(edge.to)) {
                    var distance = distances[current]!!.second + min((edge.weight ?: 0.0), distances[edge.to]!!.second)
                    if (distance < distances[edge.to]!!.second) {
                        distances[edge.to] = Pair(current, distance)
                    }
                    queue.add(edge.to)
                }
            }
        }
        var node = to
        while (node != from) {
            node = distances[node]?.first!!
        }

        return distances[to]!!.second
    }

    override fun farthestNeighbour(): Pair<List<Edge<T>>, Double> {
        val longestPaths: MutableMap<Vertex<T>, List<Edge<T>>> = mutableMapOf()
        val nodes = getVertices()
        for (i in nodes.indices) {
            longestPaths[nodes[i]] = maxDistance(nodes[i], nodes)
        }
        val longestPair = longestPaths.map { (k, v) ->
            k to v.map { it.weight }.reduce { acc, next -> acc?.plus(next!!) }
        }.maxBy { it.second!! }
        return longestPaths[longestPair.first]!! to longestPair.second!!
    }

    override fun nearestNeighbour(): Pair<List<Edge<T>>, Double> {
        val shortestPaths: MutableMap<Vertex<T>, List<Edge<T>>> = mutableMapOf()
        val nodes = getVertices()
        for (i in nodes.indices) {
            shortestPaths[nodes[i]] = minDistance(nodes[i], nodes)
        }
        shortestPaths.forEach(::println)
        val shortestPair = shortestPaths.map { (k, v) ->
            k to v.map { it.weight }.reduce { acc, next -> acc?.plus(next!!) }
        }.minBy { it.second!! }
        return Pair(shortestPaths[shortestPair.first]!!, shortestPair.second!!)
    }

    private fun minDistance(node: Vertex<T>, nodes: List<Vertex<T>>): List<Edge<T>> {
        val subset = mutableListOf<Vertex<T>>();
        subset.addAll(nodes)
        subset.remove(node)
        if (subset.size == 1) {
            val weightBetweenNodes = getWeightBetweenNodes(node, subset.first())!!
            return mutableListOf(Edge(node, subset.first(), weightBetweenNodes))
        } else {
            val zio = mutableListOf<Edge<T>>()
            var distance = Double.POSITIVE_INFINITY;
            var dist: Edge<T> = Edge(node, subset.first(), getWeightBetweenNodes(node, subset.first())!!)

            if (subset.size > 1) {
                subset.forEach { v ->
                    val min = min(distance, getWeightBetweenNodes(node, v)!!);
                    if (distance > min) {
                        distance = min
                        dist = Edge(node, v, min)
                    }
                }
            }
            zio.add(dist)
            return zio + minDistance(dist.to, subset)
        }
    }

    private fun maxDistance(node: Vertex<T>, nodes: List<Vertex<T>>): List<Edge<T>> {
        val subset = mutableListOf<Vertex<T>>();
        subset.addAll(nodes)
        subset.remove(node)
        if (subset.size == 1) {
            val weightBetweenNodes = getWeightBetweenNodes(node, subset.first())!!
            return mutableListOf(Edge(node, subset.first(), weightBetweenNodes))
        } else {
            val zio = mutableListOf<Edge<T>>()
            var distance = Double.NEGATIVE_INFINITY;
            var dist: Edge<T> = Edge(node, subset.first(), getWeightBetweenNodes(node, subset.first())!!)

            if (subset.size > 1) {
                subset.forEach { v ->
                    val max = max(distance, getWeightBetweenNodes(node, v)!!);
                    if (distance < max) {
                        distance = max
                        dist = Edge(node, v, max)
                    }
                }
            }
            zio.add(dist)
            return zio + maxDistance(dist.to, subset)
        }
    }

    override fun getNeighbors(vertex: Vertex<T>): List<Vertex<T>> {
        return adjacencyList[vertex]?.map { it.to } ?: emptyList()
    }

    // Bron-Kerbosch algorithm
    override fun getCliques(): List<Set<Vertex<T>>> {
        val maximalCliques = mutableListOf<Set<Vertex<T>>>()

        fun bronKerboschRecursive(
            r: MutableSet<Vertex<T>>,
            p: MutableSet<Vertex<T>>,
            x: MutableSet<Vertex<T>>
        ) {
            if (p.isEmpty() && x.isEmpty()) {
                maximalCliques.add(r.toSet()) // Found a maximal clique
                return
            }

            val pivot = (p + x).firstOrNull() ?: return
            val neighborsOfPivot = getNeighbors(pivot).toSet()

            for (v in p.subtract(neighborsOfPivot)) {
                val neighbors = getNeighbors(v).toSet()
                bronKerboschRecursive(
                    r.apply { add(v) },
                    p.intersect(neighbors).toMutableSet(),
                    x.intersect(neighbors).toMutableSet()
                )
                r.remove(v)
                p.remove(v)
                x.add(v)
            }
        }

        val vertices = getVertices().toMutableSet()
        bronKerboschRecursive(mutableSetOf(), vertices, mutableSetOf())
        return maximalCliques
    }
}

