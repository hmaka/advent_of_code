import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals


class Day7 {
    private val input1: String
    private val input2: String

    init {
        Day7::class.java.getResource("/day7.txt")?.let { input1 = File(it.toURI()).readText() }
            ?: error("Input File Not Found")

        Day7::class.java.getResource("/day7pt2.txt")?.let { input2 = File(it.toURI()).readText() }
            ?: error("Input File Not Found")
    }

    fun MutableMap<String, UShort>.has(s: String): Boolean {
        if (s.all { it.isDigit() }) this[s] = s.toUShort()
        return this.contains(s)
    }

    // This function is basically doing a topological sort step.
    // If we can process this line now we do so and return null,
    // otherwise we return the unprocessed line for the next sort round.
    fun process(line: List<String>, circuit: MutableMap<String, UShort>): List<String>? {
        return when {
            line.contains("AND") -> {
                when (circuit.has(line[0]) && circuit.has(line[2])) {
                    true -> {
                        circuit[line.last()] = circuit[line[0]]!! and circuit[line[2]]!!
                        null
                    }

                    false -> line
                }
            }

            line.contains("OR") -> {
                when (circuit.has(line[0]) && circuit.has(line[2])) {
                    true -> {
                        circuit[line.last()] = circuit[line[0]]!! or circuit[line[2]]!!
                        null
                    }

                    false -> line
                }
            }

            line.contains("NOT") -> {
                when (circuit.has(line[1])) {
                    true -> {
                        circuit[line.last()] = circuit[line[1]]!!.inv()
                        null
                    }

                    false -> line
                }
            }

            line.contains("LSHIFT") -> {
                when (circuit.has(line[0])) {
                    true -> {
                        circuit[line.last()] = (circuit[line[0]]!!.toUInt() shl line[2].toInt()).toUShort()
                        null
                    }

                    false -> line
                }
            }

            line.contains("RSHIFT") -> {
                when (circuit.has(line[0])) {
                    true -> {
                        circuit[line.last()] = (circuit[line[0]]!!.toUInt() shr line[2].toInt()).toUShort()
                        null
                    }

                    false -> line
                }
            }

            else -> when (circuit.has(line[0])) {
                true -> {
                    circuit[line.last()] = circuit[line[0]]!!
                    null
                }

                false -> line
            }
        }

    }

    @Test
    fun pt() {
        var instructions = input1.lines().map { it.split(Regex("\\s+")) }
        val circuit: MutableMap<String, UShort> = mutableMapOf()

        // Topological Sort
        while (instructions.isNotEmpty()) {
            instructions = instructions.mapNotNull { line -> process(line, circuit) }
        }

        val ans = circuit["a"].also { println(it) }
        assertEquals(46065.toUShort(), ans)
    }

    @Test
    fun pt2() {
        var instructions = input2.lines().map { it.split(Regex("\\s+")) }
        val circuit: MutableMap<String, UShort> = mutableMapOf()

        // Topological Sort
        while (instructions.isNotEmpty()) {
            instructions = instructions.mapNotNull { line -> process(line, circuit) }
        }

        val ans = circuit["a"].also { println(it) }
        assertEquals(14134.toUShort(), ans)
    }


}