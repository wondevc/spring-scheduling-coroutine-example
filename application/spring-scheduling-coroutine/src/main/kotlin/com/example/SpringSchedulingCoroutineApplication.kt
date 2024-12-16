package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringSchedulingCoroutineApplication

fun main(args: Array<String>) {
    runApplication<SpringSchedulingCoroutineApplication>(*args)
}
