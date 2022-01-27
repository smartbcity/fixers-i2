package i2.s2.organization.f2.service

import i2.commons.http.ClientJvm
import i2.s2.organization.f2.config.InseeConfig
import i2.s2.organization.f2.model.insee.InseeResponse
import org.springframework.stereotype.Service

@Service
class InseeHttpClient(
    private val inseeConfig: InseeConfig
): ClientJvm(
    baseUrl = inseeConfig.sireneApi,
    generateBearerToken = { inseeConfig.token }
) {
    suspend fun getOrganizationBySiret(siret: String): InseeResponse {
        return get("siret/$siret")
    }
}
