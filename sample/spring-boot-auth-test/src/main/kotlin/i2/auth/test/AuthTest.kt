package i2.auth.test

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/", produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthTest {
	@GetMapping("ping")
	fun ping() = "pong"
}