STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/i2-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}
STORYBOOK_LATEST		:= ${STORYBOOK_NAME}:latest

KEYCLOAK_DOCKERFILE	:= i2-keycloak/docker/Dockerfile
KEYCLOAK_NAME	    := smartbcity/i2-keycloak
KEYCLOAK_IMG        := ${KEYCLOAK_NAME}:${VERSION}
KEYCLOAK_LATEST		:= ${KEYCLOAK_NAME}:latest

clean: clean-java

package: package-java package-keycloak package-storybook

push: push-java push-keycloak push-storybook


clean-java:
	./gradlew clean

package-java:
	./gradlew build

push-java:
	./gradlew publish -P version=${VERSION} --info


package-keycloak:
	@docker build -f ${KEYCLOAK_DOCKERFILE} -t ${KEYCLOAK_IMG} .

push-keycloak:
	@docker push ${KEYCLOAK_IMG}

push-latest-keycloak:
	@docker tag ${KEYCLOAK_IMG} ${KEYCLOAK_LATEST}
	@docker push ${KEYCLOAK_LATEST}


package-storybook:
	@docker build -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .

push-storybook:
	@docker push ${STORYBOOK_IMG}

push-latest-storybook:
	@docker tag ${STORYBOOK_IMG} ${STORYBOOK_LATEST}
	@docker push ${STORYBOOK_LATEST}

