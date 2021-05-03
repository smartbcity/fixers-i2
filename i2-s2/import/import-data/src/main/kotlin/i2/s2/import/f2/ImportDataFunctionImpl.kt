package i2.s2.import.f2

import f2.function.spring.adapter.f2Function
import i2.s2.realm.domain.features.command.ImportDataFunction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ImportDataFunctionImpl {

	@Bean
	fun importDataFunction(): ImportDataFunction = f2Function { cmd ->
		TODO()
	}

}
