import core.permutations
import kotlin.test.Test
import java.io.File
import kotlin.test.assertEquals

typealias Person = String

class Day13 {
    private val input: String

    private val testInput = """
        Alice would gain 54 happiness units by sitting next to Bob
        Alice would lose 79 happiness units by sitting next to Carol
        Alice would lose 2 happiness units by sitting next to David
        Bob would gain 83 happiness units by sitting next to Alice
        Bob would lose 7 happiness units by sitting next to Carol
        Bob would lose 63 happiness units by sitting next to David
        Carol would lose 62 happiness units by sitting next to Alice
        Carol would gain 60 happiness units by sitting next to Bob
        Carol would gain 55 happiness units by sitting next to David
        David would gain 46 happiness units by sitting next to Alice
        David would lose 7 happiness units by sitting next to Bob
        David would gain 41 happiness units by sitting next to Carol
    """.trimIndent()


    init {
        val resource = Day13::class.java.getResource("/day13.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }

    private fun parseInput(input: String): Map<Pair<Person, Person>, Int> {
        return input.lines().map { line -> line.split(Regex("""\s+""")) }
            .associate { splitLine ->
                (splitLine.first() to splitLine.last()) to
                        (splitLine[3].toInt() * if (splitLine[2] == "lose") -1 else 1)
            }
    }

    private fun calcHappiness(table: List<Person>, prefs: Map<Pair<Person, Person>, Int>): Int {
        val table = listOf(table.last()) + table + listOf(table.first())
        return table.windowed(3)
            .sumOf { win ->
                val (p1, p2, p3) = win
                prefs.getOrDefault(p2 to p1, 0) + prefs.getOrDefault(p2 to p3, 0)
            }
    }

    @Test
    fun testInput() {
        val prefs = parseInput(testInput)
        val ans =
            prefs.keys.flatMap { it.toList() }.distinct()
                .permutations().maxOf { table -> calcHappiness(table, prefs) }
        assertEquals(330, ans)
    }

    @Test
    fun pt1() {
        val prefs = parseInput(input)
        val ans =
            prefs.keys.flatMap { it.toList() }.distinct()
                .permutations().maxOf { table -> calcHappiness(table, prefs) }
        assertEquals(709, ans)
    }

    @Test
    fun pt2() {
        val prefs = parseInput(input) + (("Havish" to "Havish") to 0)
        val ans =
            prefs.keys.flatMap { it.toList() }.distinct()
                .permutations().maxOf { table -> calcHappiness(table, prefs) }
        assertEquals(668, ans)
    }
}