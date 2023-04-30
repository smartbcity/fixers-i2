STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/i2-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}

KEYCLOAK_DOCKERFILE	:= i2-keycloak/docker/Dockerfile
KEYCLOAK_NAME	    := smartbcity/i2-keycloak
KEYCLOAK_IMG        := ${KEYCLOAK_NAME}:${VERSION}

I2_INIT_NAME	   	:= smartbcity/i2-init
I2_INIT_IMG	    	:= ${I2_INIT_NAME}:${VERSION}
I2_INIT_PACKAGE	   	:= :i2-app:init:app-init-gateway:bootBuildImage

I2_CONFIG_NAME	   	:= smartbcity/i2-config
I2_CONFIG_IMG	    := ${I2_CONFIG_NAME}:${VERSION}
I2_CONFIG_PACKAGE	:= :i2-app:config:app-config-gateway:bootBuildImage

libs: package-kotlin
docker: docker-keycloak docker-init package-config
docs: package-storybook

package-kotlin:
	./gradlew build publish -x test --stacktrace

package-storybook:
	@docker build -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .
	@docker push ${STORYBOOK_IMG}

docker-keycloak:
	./gradlew i2-keycloak:keycloak-plugin:shadowJar
	@docker build -f ${KEYCLOAK_DOCKERFILE} -t ${KEYCLOAK_IMG} .
	@docker push ${KEYCLOAK_IMG}

docker-init:
	VERSION=${VERSION} ./gradlew build ${I2_INIT_PACKAGE} -x test --stacktrace
	@docker push ${I2_INIT_IMG}

docker-config:
	VERSION=${VERSION} ./gradlew build ${I2_CONFIG_PACKAGE} -x test --stacktrace
	@docker push ${I2_CONFIG_IMG}
