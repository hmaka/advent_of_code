import core.getNumbersFrom
import kotlin.test.Test
import java.io.File
import kotlin.test.assertEquals
import kotlinx.serialization.json.*
import kotlin.collections.component1
import kotlin.collections.component2

class Day12 {
    private val input: String
    private val jsonInput: JsonElement

    init {
        val resource = Day12::class.java.getResource("/day12.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
        jsonInput = Json.parseToJsonElement(input)
    }

    private fun typeHandler(jsonElement: JsonElement): Int {
        return when (jsonElement) {
            is JsonPrimitive -> {
                jsonElement.jsonPrimitive.intOrNull ?: 0
            }

            is JsonArray -> {
                jsonElement.jsonArray.sumOf { typeHandler(it) }
            }

            is JsonObject -> {
                val obj = jsonElement.jsonObject
                val hasRed =
                    obj.values.filter { it is JsonPrimitive }.map { it.jsonPrimitive.content }.any { it == "red" }
                if (hasRed) 0
                else obj.map { (_, v) -> typeHandler(v) }.sum()
            }
        }
    }

    @Test
    fun pt1() {
        val ans = input.getNumbersFrom().sum().also { println(it) }
        assertEquals(191164, ans)
    }

    @Test
    fun pt2() {
        val ans = typeHandler(jsonInput).also { println(it) }
        assertEquals(87842, ans)
    }
}