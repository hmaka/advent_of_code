import kotlin.test.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day11 {
    private val input: String

    init {

        input = "cqjxjnds"
//        val resource = Day11::class.java.getResource("/day11.txt")
//        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }

    private fun incrementChar(char: Char): Char {
        if (char == 'z') return 'a'
        return char + 1
    }

    private fun incrementPassword(s: List<Char>): List<Char> {
        if (s.isEmpty()) error("tried to increment past last letter")
        return when (val last = incrementChar(s.last())) {
            'a' -> incrementPassword(s.dropLast(1)) + last
            else -> s.dropLast(1) + last
        }
    }

    private fun isValidPassword(s: List<Char>): Boolean {
        val threeIncreasing = s.windowed(3).any { win -> win[0] + 1 == win[1] && win[1] + 1 == win[2] }
        val doesNotContainForbiddenLetters = "iol".any { s.contains(it) }.not()
        val twoDifferentNonOverLapPairs = s.windowed(2).filter { win -> win.first() == win.last() }.distinct().size >= 2

        return threeIncreasing && doesNotContainForbiddenLetters && twoDifferentNonOverLapPairs
    }

/*
    I actually solved this problem by reasoning.
    We want the next password so we should try to change letters on the right to get the "closest" one.
    Our password will be of the form "aabcc" to meet requirements 1,3.
    Ideally we can keep the 4th letter the same. In our case it's 'x' and since all the letters to the right of
    'x' are 'less than' x that means we can rotate them 'up' to 'x'. So then we can get "cqjxxyzz".

    For part 2 we notice that in order to get futher we would need to increment the right most 'z' which means incrementing
    the next 'z' and so on. So at best we can't have say "yyz--" as the postfix because the dashes would have to be 'a'.
    It's safe to assume then the next postfix will be "aabcc" and so then that means the third letter has to rotate up once
    (since the 5th letter 'x' rotated around to 'a'). Giving us "cqkaabcc".

 */
    @Test
    fun pt1() {
        var ans = input.toList()
        while (isValidPassword(ans).not()) {
            ans = incrementPassword(ans)
        }

        assertEquals("cqjxxyzz",ans.joinToString(separator = ""))
    }

    @Test
    fun pt2() {
        var ans = "cqjxxzaa".toList()
        while (isValidPassword(ans).not()) {
            ans = incrementPassword(ans)
        }

        assertEquals("cqkaabcc",ans.joinToString(separator = ""))
    }
}