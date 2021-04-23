STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/i2-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}
STORYBOOK_LATEST		:= ${STORYBOOK_NAME}:latest


clean: clean-java

test: test-java

package: package-java package-storybook

push: push-java push-storybook

clean-java:
	./gradlew clean

package-java:
	./gradlew build

push-java:
	./gradlew publish -P version=${VERSION} --info

package-storybook:
	@docker build -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .

push-storybook:
	@docker push ${STORYBOOK_IMG}

push-latest-storybook:
	@docker tag ${STORYBOOK_IMG} ${STORYBOOK_LATEST}
	@docker push ${STORYBOOK_LATEST}
