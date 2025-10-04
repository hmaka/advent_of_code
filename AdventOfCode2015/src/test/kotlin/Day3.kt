import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

data class State(var x: Int, var y: Int, var seen: MutableMap<Pair<Int,Int>,Int>)

class Day3 {
    private val input: String

    init {
        val resource = Day3::class.java.getResource("/day3.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }

    @Test
    fun p1(){
        val state = State(
            0,0,
            mutableMapOf(Pair(0,0) to 1).withDefault { _ -> 0 }
        )

        input.forEach {
            simulate(it,state)
                //.also { println("x: ${state.x}, y: ${state.y} with ${state.seen.keys.size} unique visits") }
        }
        val ans = state.seen.keys.size
        println(ans)
        assertEquals(2081,ans)
    }

    @Test
    fun p2(){
        val santa_state = State(
            0,0,
            mutableMapOf(Pair(0,0) to 1).withDefault { _ -> 0 }
        )

        val robo_state = State(
            0,0,
            mutableMapOf(Pair(0,0) to 1).withDefault { _ -> 0 }
        )

        input.chunked(2)
            .map{Pair(it[0],it[1])}
            .forEach { (inst1, inst2) ->
                simulate(inst1,santa_state)
                simulate(inst2,robo_state)
            }
        val ans = (santa_state.seen.keys+robo_state.seen.keys).toSet().size
        println(ans)
        assertEquals(2341,ans)
    }

    fun simulate(instruction:Char, state: State) {
        val newPos = when (instruction) {
            '>' -> Pair(state.x + 1, state.y)
            '<' -> Pair(state.x - 1, state.y)
            '^' -> Pair(state.x, state.y + 1)
            'v' -> Pair(state.x, state.y - 1)
            else -> error("Invalid char: $instruction")
        }
        state.seen[newPos] = state.seen.getOrDefault(Pair(state.x,state.y),0)
        state.x = newPos.first
        state.y = newPos.second
    }
}