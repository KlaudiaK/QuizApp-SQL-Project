package pl.poznan.put.quizzy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@SpringBootApplication
@Configuration
@EnableJpaRepositories
class BackendRestApiApplication {

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<BackendRestApiApplication>(*args)
		}
	}
}
