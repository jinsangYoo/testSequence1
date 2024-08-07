package org.example

import java.math.BigInteger
import kotlin.random.Random

val fibonacci: Sequence<BigInteger> = sequence {
    var first = 0.toBigInteger()
    var second = 1.toBigInteger()
    while (true) {
        yield(first)
        val temp = first
        first += second
        second = temp
    }
}

fun randomNumbers(
    seed: Long = System.currentTimeMillis(),
    min: Int = 0,
    max: Int = 100
): Sequence<Int> = sequence {
    val random = Random(seed)
    while (true) {
        yield(random.nextInt(from = min, until = max))
    }
}.distinct()

fun randomUniqueStrings(
    length: Int,
    seed: Long = System.currentTimeMillis()
): Sequence<String> = sequence {
    val random = Random(seed)
    val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    while (true) {
        val randomString = (1..length)
            .map { i -> random.nextInt(charPool.size) }
            .map (charPool::get)
            .joinToString("")
        yield(randomString)
    }
}.distinct()

fun main() {
    println(fibonacci.take(10).toList())
    println(randomNumbers(min = 1, max = 46).take(10).toList())
    val it = randomUniqueStrings(3).iterator()
    println(it.next())
    println(it.next())
    println(it.next())
}