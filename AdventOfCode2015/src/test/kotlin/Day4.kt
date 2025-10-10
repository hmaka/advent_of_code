import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import java.math.BigInteger
import java.security.MessageDigest

data class State(var x: Int, var y: Int, var seen: MutableMap<Pair<Int,Int>,Int>)

class Day4 {
    private val input: String

    init {
        val resource = Day4::class.java.getResource("/day4.txt")
        resource?.let { input = File(it.toURI()).readText() } ?: error("Input File Not Found")
    }

   @Test
   fun pt1(){
       var candidate = 0
       while (!md5(input + candidate.toString()).startsWith("00000")){
           candidate += 1
       }
       println(candidate)
   }

    fun pt2(){
        var candidate = 0
        while (!md5(input + candidate.toString()).startsWith("000000")){
            candidate += 1
        }
        println(candidate)
    }

     fun md5(input: String): String {
         val md = MessageDigest.getInstance("MD5")
         val digest = md.digest(input.toByteArray())
         return BigInteger(1, digest)
             .toString(16)
             .padStart(32, '0')
     }
}