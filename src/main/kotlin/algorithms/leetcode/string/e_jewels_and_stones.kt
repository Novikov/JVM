package algorithms.leetcode.string

fun main(){
    val jewels = "aA"
    val stones = "aAAbbbb"
    val nums = numJewelsInStones(jewels, stones)
    println(nums)
}

fun numJewelsInStones(jewels: String, stones: String): Int {
    val counterMap = hashMapOf<Char, Int>()
    //Заполняем теблицу
    for(jewel in jewels){
        counterMap.put(jewel, 0)
    }

    //Считаем драгоценности по каждой позиции
    for(stone in stones){
        // Если камень есть то увеличиваем индекс
        if (counterMap.contains(stone)){
            val newIndex = (counterMap.get(stone) ?: 0) + 1
            counterMap[stone] = newIndex
        }
    }

    return counterMap.map{ it.value }.sum()
}