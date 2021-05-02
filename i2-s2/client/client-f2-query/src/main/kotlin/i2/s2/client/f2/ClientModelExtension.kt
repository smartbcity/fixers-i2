package i2.s2.client.f2

import i2.s2.client.domain.ClientModel
import org.keycloak.representations.idm.ClientRepresentation

fun List<ClientRepresentation>.asModels(): List<ClientModel> = map(ClientRepresentation::asModel)

fun ClientRepresentation.asModel(): ClientModel {
	return ClientModel(
		id = this.id,
		clientIdentifier = this.clientId
	)
}