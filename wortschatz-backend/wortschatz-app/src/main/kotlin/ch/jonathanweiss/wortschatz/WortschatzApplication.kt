package ch.jonathanweiss.wortschatz

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WortschatzApplication

fun main(args: Array<String>) {
    runApplication<WortschatzApplication>(*args)
}
