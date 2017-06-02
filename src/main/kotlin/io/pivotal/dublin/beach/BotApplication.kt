package io.pivotal.dublin.beach

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = arrayOf("me.ramswaroop.jbot", "io.pivotal.dublin.beach"))
class BotApplication

fun main(args: Array<String>) {
    SpringApplication.run(BotApplication::class.java, *args)
}
