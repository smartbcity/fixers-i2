package i2.f2.organization.app.service

import i2.commons.http.ClientJvm
import i2.f2.organization.app.config.InseeConfig
import i2.f2.organization.app.model.insee.InseeResponse
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
