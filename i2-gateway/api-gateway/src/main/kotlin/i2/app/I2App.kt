package i2.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [I2App::class] )
class I2App

fun main(args: Array<String>) {
	runApplication<I2App>(*args)
}
