import core.cache
import kotlin.test.Test
import java.io.File
import kotlin.test.assertEquals

class Day9 {
    private val input: String
    private val N: Int
    private val cost: Map<Pair<Int, Int>, Int>

    init {
        val resource = Day9::class.java.getResource("/day9.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
        val tmp = parseInput(input)
        N = tmp.first
        cost = tmp.second
    }

    private fun parseInput(s: String): Pair<Int, Map<Pair<Int, Int>, Int>> {
        val splitLines = s.lines().map { it.split(Regex("""\s+""")) }
        val cities =
            splitLines.flatMap { line -> listOf(line[0], line[2]) }.distinct().withIndex()
                .associate { (i, s) -> s to i }
        val cityToCityCost = splitLines
            .flatMap { line ->
                listOf(
                    Triple(line[0], line[2], line.last().toInt()),
                    Triple(line[2], line[0], line.last().toInt()),
                )
            }
            .associate { (start, end, dist) -> (cities[start]!! to cities[end]!!) to dist }
        return cities.size to cityToCityCost
    }

    // Traveling sales-men solver
    private fun shortestDistanceRoute(curr: Int, visited: Set<Int>): Int {
        if (visited.size == N) return 0

        return (0..<N)
            .filter { it != curr } // skip current city
            .filter { !visited.contains(it) } // skip visited cities
            .filter { (curr to it) in cost } // path must be possible
            .minOf { city ->
                cost[curr to city]!! + shortestDistanceRoute(city, visited.plus(city))
            }
    }

    private fun longestDistanceRoute(curr: Int, visited: Set<Int>): Int {
        if (visited.size == N) return 0

        return (0..<N)
            .filter { it != curr } // skip current city
            .filter { !visited.contains(it) } // skip visited cities
            .filter { (curr to it) in cost } // path must be possible
            .maxOf { city ->
                cost[curr to city]!! + longestDistanceRoute(city, visited.plus(city))
            }
    }

    @Test
    fun pt1() {
        // We parse input in init block
        val cachedSolver = cache(this::shortestDistanceRoute)
        val ans = (0..<N).mapNotNull {
            cachedSolver(it, setOf(it))
        }.min().also { println(it) }

        assertEquals(117,ans)

    }

    @Test
    fun pt2() {
        val cachedSolver = cache(this::longestDistanceRoute)
        val ans = (0..<N).mapNotNull {
            cachedSolver(it, setOf(it))
        }.max().also { println(it) }

        assertEquals(909,ans)
    }
}