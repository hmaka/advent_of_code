package core

typealias Board = List<List<Int>>
typealias V = Pair<Int, Int>


// Utility
fun Board.get2dOrNull(p: V): Int? {
    return this.getOrNull(p.first)?.getOrNull(p.second)
}

operator fun V.plus(other: V): V {
    return V(this.first + other.first, this.second + other.second)
}

operator fun V.minus(other: V): V {
    return V(this.first - other.first, this.second - other.second)
}

operator  fun V.compareTo(other: V): Int {
    return compareValuesBy(this, other, { it.first }, { it.second })
}

fun <T, R> List<List<T>>.map2D(transform: (V, T) -> R): List<List<R>> {
    return this.mapIndexed { i, row -> row.mapIndexed { j, element -> transform(V(i, j), element) } }
}

fun <T, R> List<List<T>>.flatMap2D(transform: (V, T) -> R): List<R> {
    return this.flatMapIndexed { i, row -> row.mapIndexed { j, element -> transform(V(i, j), element) } }
}

fun <T, R> List<List<T>>.flatMap2DNotNull(transform: (V, T) -> R?): List<R> {
    return this.flatMapIndexed { i, row -> row.mapIndexedNotNull { j, element -> transform(V(i, j), element) } }
}


fun getNumbersFrom(input: String): List<Int> {
    val regex = Regex("-?\\d+")
    return regex.findAll(input)
        .map { it.value.toInt() }
        .toList()
}

fun<T> groupAdajcentBy(input: Iterable<T>, cmp: (prev: T, current: T) -> Boolean): List<List<T>> {
    return input.fold(mutableListOf<MutableList<T>>()) { groups, item ->
        val lastGroup = groups.lastOrNull()

        when {
            lastGroup == null || !cmp(lastGroup.last(), item) ->
                groups.add(mutableListOf(item))
            else -> lastGroup.add(item)
        }
        groups
    }
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val name = "Kotlin"
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    println("Hello, " + name + "!")

    for (i in 1..5) {
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        println("i = $i")
    }
}