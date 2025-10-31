import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals


class Day5 {
    private val input: String

    init {
        val resource = Day5::class.java.getResource("/day5.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }

    @Test
    fun pt1(){
        val ans = input.lines().map { isNiceStringPt1(it) }.map { if (it) 1 else 0 }.sum().also { println(it) }
        assertEquals(238,ans)
    }

    @Test
    fun pt2(){
        val ans = input.lines().map { isNiceStringPt2(it) }.map { if (it) 1 else 0 }.sum().also { println(it) }
        assertEquals(69,ans)
    }

    private fun isNiceStringPt1(s: String): Boolean {
        val hasThreeVowels = s.map { if ("aeiou".contains(it)) 1 else 0 }.sum() >= 3
        val hasRepeatedLetter = s.contains(Regex("([a-z])\\1"))
        val doesNotContainBadStrings = listOf("ab", "cd", "pq", "xy").map { !s.contains(it) }.all { it }

        return hasThreeVowels && hasRepeatedLetter && doesNotContainBadStrings
    }

    private fun isNiceStringPt2(s: String): Boolean {
        val hasRepeatedLetterPair = s.contains(Regex("([a-z]{2}).*\\1"))
        val twoSameLettersWithLetterBetweenThem = s.contains(Regex("([a-z])[a-z]\\1"))

        return hasRepeatedLetterPair && twoSameLettersWithLetterBetweenThem
    }

}