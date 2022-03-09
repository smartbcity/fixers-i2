STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/i2-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}
STORYBOOK_LATEST		:= ${STORYBOOK_NAME}:latest

KEYCLOAK_DOCKERFILE	:= i2-keycloak/docker/Dockerfile
KEYCLOAK_NAME	    := smartbcity/i2-keycloak
KEYCLOAK_IMG        := ${KEYCLOAK_NAME}:${VERSION}
KEYCLOAK_LATEST		:= ${KEYCLOAK_NAME}:latest

I2_APP_NAME	   	 	:= smartbcity/i2-gateway
I2_APP_IMG	    	:= ${I2_APP_NAME}:${VERSION}
I2_APP_LATEST		:= ${I2_APP_NAME}:latest
I2_APP_PACKAGE	   	:= :sample:spring-boot-app:bootBuildImage

I2_INIT_NAME	   	:= smartbcity/i2-init
I2_INIT_IMG	    	:= ${I2_INIT_NAME}:${VERSION}
I2_INIT_LATEST		:= ${I2_INIT_NAME}:latest
I2_INIT_PACKAGE	   	:= :i2-init:api-init:bootBuildImage

libs: package-kotlin
docker: package-keycloak package-app
docs: package-storybook

package-kotlin:
	@gradle clean build publish --stacktrace

package-storybook:
	@docker build -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .
	@docker push ${STORYBOOK_IMG}

package-keycloak:
	@docker build -f ${KEYCLOAK_DOCKERFILE} -t ${KEYCLOAK_IMG} .
	@docker push ${KEYCLOAK_IMG}

package-app:
	VERSION=${VERSION} ./gradlew build ${I2_APP_PACKAGE} -x test --stacktrace
	@docker push ${I2_APP_IMG}

package-init:
	VERSION=${VERSION} ./gradlew build ${I2_INIT_PACKAGE} -x test --stacktrace
	@docker push ${I2_INIT_IMG}
