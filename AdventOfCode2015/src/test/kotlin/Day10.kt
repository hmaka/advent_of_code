import core.groupAdajcentBy
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

typealias ConwayElement = String
typealias ConwayElementString = String

class Day10 {
    private val input: String
    private val conwayMapInput: String

    init {
        input = "1321131112"
        val resource = Day8::class.java.getResource("/day10ConwayMap.txt")
        resource?.let { conwayMapInput = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }


    private fun parseConwayMap(s: String):
            Pair<Map<ConwayElement, List<ConwayElement>>,
                    Map<ConwayElement, ConwayElementString>> {
        val byLineAndSplit = s.lines().map { it.trim().split(Regex("""\s+""")) }
        val elementToElements = byLineAndSplit.associate { line ->
            line.first() to line.drop(1).dropLast(2)
        }
        val elementToElementString = byLineAndSplit.associate { line ->
            line.first() to line[line.size - 2]
        }
        return elementToElements to elementToElementString
    }

    private fun lookAndSay(s: String): String {
        val grouped = s.toList().groupAdajcentBy { prev, current -> prev == current }
        return grouped.joinToString(separator = "") { grp ->
            grp.size.toString() + grp.first()
        }
    }

    private fun conwayEvolutionSimulator(
        conwayMap: Map<ConwayElement, List<ConwayElement>>,
        elements: List<ConwayElement>
    ): List<ConwayElement> {
        return elements.flatMap { element -> conwayMap[element]!! }
    }

    @Test
    fun pt1() {
        val ans = (0 until 40)
            .fold(input) { s, _ -> lookAndSay(s) }
            .length.also { println(it) }

        assertEquals(492982, ans)
    }

    @Test
    fun pt2() {
        val (elementToElement, elementToString) = parseConwayMap(conwayMapInput)
        val myStartingElement = "Yb"
        val ans = (0 until 50)
            .fold(listOf(myStartingElement)) { elements, _ ->
                conwayEvolutionSimulator(elementToElement, elements)
            }
            .joinToString(separator = "") { element -> elementToString[element]!! }
            .length

        assertEquals(6989950, ans)
    }
}