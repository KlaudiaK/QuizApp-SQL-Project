package pl.poznan.put.quizzy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendRestApiApplication

fun main(args: Array<String>) {
	runApplication<BackendRestApiApplication>(*args)
}
