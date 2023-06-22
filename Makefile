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
docker: docker-build docker-push
docs: package-storybook

docker-build: docker-keycloak-build docker-init-build docker-config-build
docker-push: docker-keycloak-push docker-init-push docker-config-push

package-kotlin:
	./gradlew build publish -x test --stacktrace

package-storybook:
	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .
	@docker push ${STORYBOOK_IMG}

docker-keycloak-build:
	./gradlew i2-keycloak:keycloak-plugin:shadowJar
	@docker build -f ${KEYCLOAK_DOCKERFILE} -t ${KEYCLOAK_IMG} .

docker-keycloak-push:
	@docker push ${KEYCLOAK_IMG}

docker-init-build:
	VERSION=${VERSION} ./gradlew build ${I2_INIT_PACKAGE} -x test --stacktrace

docker-init-push:
	@docker push ${I2_INIT_IMG}

docker-config-build:
	VERSION=${VERSION} ./gradlew build ${I2_CONFIG_PACKAGE} -x test --stacktrace

docker-config-push:
	@docker push ${I2_CONFIG_IMG}
