package i2.init.api.init

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan

@EntityScan("i2.init")
@SpringBootApplication(scanBasePackages = ["i2.init"])
class InitApplication

fun main(args: Array<String>) {
	SpringApplication(InitApplication::class.java).run {
		run(*args)
	}
}
