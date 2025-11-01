import core.*
import java.io.File
import kotlin.math.max
import kotlin.test.Test
import kotlin.test.assertEquals


class Day6 {
    private val input: String

    init {
        val resource = Day6::class.java.getResource("/day6.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }

    enum class Switch {
        On,
        Off,
        Flip,
    }

    private fun initBoard(): Board {
        return List(1000) { List(1000) { 0 } }
    }

    private fun flip1(board: Board, switch: Switch, from: V, to: V): Board {
        return board.map2D { (i,j), x ->
            val (fi,fj) = from
            val (ti, tj) = to
            when (i in fi..ti && j in fj..tj) {
                true -> when (switch) {
                    Switch.On -> 1
                    Switch.Off -> 0
                    Switch.Flip -> x xor 1
                }
                false -> x
            }
        }
    }

    private fun flip2(board: Board, switch: Switch, from: V, to: V): Board {
        return board.map2D { (i,j), x ->
            val (fi,fj) = from
            val (ti, tj) = to
            when (i in fi..ti && j in fj..tj) {
                true -> when (switch) {
                    Switch.On -> x + 1
                    Switch.Off -> max(0,x - 1)
                    Switch.Flip -> x + 2
                }
                false -> x
            }
        }
    }


    @Test
    fun pt1() {
        val board = initBoard()
        val instrs = input.lines().map { line ->
            val switch = when {
                line.contains("on") -> Switch.On
                line.contains("off") -> Switch.Off
                line.contains("toggle") -> Switch.Flip
                else -> error("invalid input")
            }
            val nums = getNumbersFrom(line)
            Triple(switch,V(nums[0],nums[1]),V(nums[2],nums[3]))
        }

        val ans = instrs
            .fold(board) { brd, (on, from, to) -> flip1(brd, on, from, to) }
            .flatMap2D { _, x -> x }.sum()
            .also { println(it)}
        assertEquals(377891,ans)

    }

    @Test
    fun pt2() {
        val board = initBoard()
        val instrs = input.lines().map { line ->
            val switch = when {
                line.contains("on") -> Switch.On
                line.contains("off") -> Switch.Off
                line.contains("toggle") -> Switch.Flip
                else -> error("invalid input")
            }
            val nums = getNumbersFrom(line)
            Triple(switch, V(nums[0], nums[1]), V(nums[3], nums[4]))
        }

        val ans = instrs
            .fold(board) { brd, (on, from, to) -> flip2(brd, on, from, to) }
            .flatMap2D { _, x -> x }.sum()
            .also { println(it)}
        assertEquals(377891,ans)

    }


}