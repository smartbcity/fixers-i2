package i2.auth.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [AuthTestApp::class] )
class AuthTestApp

fun main(args: Array<String>) {
	runApplication<AuthTestApp>(*args)
}
