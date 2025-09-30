import kotlin.test.Test
import java.io.File
import kotlin.test.assertEquals

class Day1 {
    private val input: String

    init {
        val resource = Day1::class.java.getResource("/day1.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }

    @Test
    fun pt1() {
        val ans = input.map {
            when (it) {
                '(' -> 1
                else -> -1
            }
        }.sum()
        println(ans)
        assertEquals(138,ans)
    }

    @Test
    fun pt2() {
        val ans = input.map {
            when (it) {
                '(' -> 1
                else -> -1
            }
        }.runningReduce { acc, it -> acc + it }.indexOfFirst { it == -1 } + 1
        println(ans)
        assertEquals(1771,ans)
    }
}