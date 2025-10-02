class Helpers {

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

}