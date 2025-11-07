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

operator fun V.compareTo(other: V): Int {
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


fun String.getNumbersFrom(): List<Int> {
    val regex = Regex("-?\\d+")
    return regex.findAll(this)
        .map { it.value.toInt() }
        .toList()
}

fun <T> Iterable<T>.groupAdajcentBy(cmp: (prev: T, current: T) -> Boolean): List<List<T>> {
    return this.fold(mutableListOf<MutableList<T>>()) { groups, item ->
        val lastGroup = groups.lastOrNull()

        when {
            lastGroup == null || !cmp(lastGroup.last(), item) ->
                groups.add(mutableListOf(item))

            else -> lastGroup.add(item)
        }
        groups
    }
}

fun <A, B, R> cache(f: ((A, B) -> R)): ((A, B) -> R) {
    val memo = mutableMapOf<Pair<A, B>, R>()
    return { a: A, b: B ->
        memo.getOrPut(a to b) { f(a, b) }
    }
}