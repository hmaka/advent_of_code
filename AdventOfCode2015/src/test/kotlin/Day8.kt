import kotlin.test.Test
import java.io.File
import kotlin.test.assertEquals

class Day8 {
    private val input: String

    init {
        val resource = Day8::class.java.getResource("/day8.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }

    private fun unEscape(s: String): String {
        // Using escaped characters to capture
        return s.replace("\\\\", "\\")
            .replace("\\\"", "\"")
            .replace(Regex("\\\\x([0-9a-fA-F]{2})")) { matchResult ->
                matchResult.groupValues[1].toInt(16).toChar().toString()
            }
    }

    private fun escape(s: String): String {
        // Using raw strings to capture
        return """"\"""" + s.replace("""\\""", """\\\\""")
            .replace("""\"""", """\\\"""")
            .replace(Regex("\\\\(x[0-9a-fA-F]{2})")) { matchResult ->
                """\\""" + matchResult.groupValues[1]
            } + """\"""""
    }

    @Test
    fun pt1() {
        val removedQuotes = input.lines().map { it.removeSurrounding("\"") }
        val original = input.lines().map { it to it.length }
        val unEscaped = removedQuotes.map { unEscape(it) }.map { it to it.length }
        val ans = original.zip(unEscaped).sumOf { (o, un) -> o.second - un.second }.also { println(it) }
        assertEquals(1350, ans)
    }

    @Test
    fun pt2() {
        val removedQuotes = input.lines().map { it.removeSurrounding("\"") }
        val original = input.lines().map { it to it.length }
        val escaped = removedQuotes.map { escape(it) }.map { it to it.length }
        val ans = original.zip(escaped).sumOf { (o, es) -> es.second - o.second }.also { println(it) }
        assertEquals(2085, ans)
    }
}