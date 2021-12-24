STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/i2-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}
STORYBOOK_LATEST		:= ${STORYBOOK_NAME}:latest

KEYCLOAK_DOCKERFILE	:= i2-keycloak/docker/Dockerfile
KEYCLOAK_NAME	    := smartbcity/i2-keycloak
KEYCLOAK_IMG        := ${KEYCLOAK_NAME}:${VERSION}
KEYCLOAK_LATEST		:= ${KEYCLOAK_NAME}:latest

libs: package-kotlin package-keycloak
docs: package-storybook

package-kotlin:
	@gradle clean build publish --stacktrace

package-storybook:
	@docker build -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .
	@docker push ${STORYBOOK_IMG}

package-keycloak:
	@docker build -f ${KEYCLOAK_DOCKERFILE} -t ${KEYCLOAK_IMG} .
	@docker push ${KEYCLOAK_IMG}
