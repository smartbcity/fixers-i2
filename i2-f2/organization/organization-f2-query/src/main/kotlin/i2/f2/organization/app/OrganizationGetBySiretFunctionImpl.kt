package i2.f2.organization.app

import f2.dsl.fnc.f2Function
import i2.commons.model.AddressBase
import i2.f2.organization.app.model.insee.InseeAddress
import i2.f2.organization.app.model.insee.InseeOrganization
import i2.f2.organization.app.service.InseeHttpClient
import i2.f2.organization.domain.features.query.OrganizationGetBySiretFunction
import i2.f2.organization.domain.features.query.OrganizationGetBySiretResult
import i2.f2.organization.domain.model.OrganizationBase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationGetBySiretFunctionImpl(
	private val inseeHttpClient: InseeHttpClient
) {

	@Bean
	fun organizationGetBySiretFunction(): OrganizationGetBySiretFunction = f2Function { cmd ->
		val organizationDetails = try {
			inseeHttpClient.getOrganizationBySiret(cmd.siret)
		} catch (e: Exception) {
			e.printStackTrace()
			null
		}

		organizationDetails?.etablissement
			?.toOrganization()
			.let(::OrganizationGetBySiretResult)
	}

	private fun InseeOrganization.toOrganization() = OrganizationBase(
		id = "",
		siret = siret,
		name = uniteLegale.denominationUniteLegale.orEmpty(),
		description = null,
		address = adresseEtablissement.toAddress(),
		website = null,
		roles = null
	)

	private fun InseeAddress.toAddress() = AddressBase(
		street = street(),
		postalCode = codePostalEtablissement.orEmpty(),
		city = libelleCommuneEtablissement.orEmpty()
	)

	private fun InseeAddress.street() = StringBuilder().apply {
		numeroVoieEtablissement?.let { append("$it ") }
		indiceRepetitionEtablissement?.let { append("$it ") }
		typeVoieEtablissement?.let { append("$it ") }
		libelleVoieEtablissement?.let { append(it) }
	}.toString()
}
