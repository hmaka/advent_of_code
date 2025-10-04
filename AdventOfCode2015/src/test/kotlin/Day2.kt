import java.io.File
import java.util.Collections.min
import kotlin.test.Test
import kotlin.test.assertEquals

class Day2 {
    private val input: String
    private val helpers: Helpers = Helpers()

    init {
        val resource = Day2::class.java.getResource("/day2.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }


    @Test
    fun pt1(){
        val nums = helpers.getNumbersFrom(input).chunked(3)
        val ans:Int = nums
            .map{ (l,w,h) -> Triple(l*w,  w*h,  h*l)}
            .also {println(it)}
            .sumOf{(s1,s2,s3) -> 2*s1 + 2*s2 + 2*s3 + min(listOf(s1,s2,s3))}
            .also { println(it) }
        assertEquals(1586300,ans)
    }

    @Test
    fun pt2(){
        val nums = helpers.getNumbersFrom(input).chunked(3)
        val ans = nums
            .map { it.sorted() }
            .also { println(it) }
            .sumOf {(l,w,h) -> l*2 + w*2 + l*w*h}
            .also { println(it) }
        assertEquals(3737498,ans)
    }
}