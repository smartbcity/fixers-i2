package i2.s2.organization.f2

import f2.dsl.fnc.f2Function
import i2.s2.organization.domain.features.query.OrganizationGetSiretDetailsQueryFunction
import i2.s2.organization.domain.features.query.OrganizationGetSiretDetailsQueryResult
import i2.s2.organization.domain.model.AddressBase
import i2.s2.organization.domain.model.OrganizationBase
import i2.s2.organization.f2.model.insee.InseeAddress
import i2.s2.organization.f2.model.insee.InseeOrganization
import i2.s2.organization.f2.service.InseeHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrganizationGetSiretDetailsQueryFunctionImpl(
	private val inseeHttpClient: InseeHttpClient
) {

	@Bean
	fun organizationGetSiretDetailsQueryFunction(): OrganizationGetSiretDetailsQueryFunction = f2Function { cmd ->
		val organizationDetails = try {
			inseeHttpClient.getOrganizationBySiret(cmd.siret)
		} catch (e: Exception) {
			e.printStackTrace()
			null
		}

		organizationDetails?.etablissement
			?.toOrganization()
			.let(::OrganizationGetSiretDetailsQueryResult)
	}

	private fun InseeOrganization.toOrganization() = OrganizationBase(
		id = "",
		siret = siret,
		name = uniteLegale.denominationUniteLegale,
		description = null,
		address = adresseEtablissement.toAddress()
	)

	private fun InseeAddress.toAddress() = AddressBase(
		street = "$numeroVoieEtablissement $indiceRepetitionEtablissement $typeVoieEtablissement $libelleVoieEtablissement",
		postalCode = codePostalEtablissement,
		city = libelleCommuneEtablissement
	)
}
