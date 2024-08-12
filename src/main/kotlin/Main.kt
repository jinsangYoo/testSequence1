package org.example

import java.math.BigInteger
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
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

fun launchScheduler(
    initDelay: Long = 3000,
    delay: Long = 3000
): ScheduledExecutorService {
    val scheduler = Executors.newSingleThreadScheduledExecutor()
    scheduler.scheduleWithFixedDelay({
        println("!!!")
    }, initDelay, delay, TimeUnit.MILLISECONDS)

    return scheduler
}

fun shutDownSchedule(
    delay: Long = 9000,
    scheduler: ScheduledExecutorService
) {
    val _scheduler = Executors.newSingleThreadScheduledExecutor()
    _scheduler.schedule({
        println("try scheduler shutdown")
        scheduler.shutdown()
        println("try _scheduler shutdown")
        _scheduler.shutdown()
    }, delay, TimeUnit.MILLISECONDS)
}

fun main() {
    println(fibonacci.take(10).toList())
    println(randomNumbers(min = 1, max = 46).take(10).toList())
    val it = randomUniqueStrings(3).iterator()
    println(it.next())
    println(it.next())
    println(it.next())

    val scheduler = launchScheduler(initDelay = 0, delay = 2000)
    shutDownSchedule(delay = 10000, scheduler = scheduler)
}