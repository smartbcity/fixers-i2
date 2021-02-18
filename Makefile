clean: clean-java

test: test-java

package: package-java

push: push-java

clean-java:
	./gradlew clean

package-java:
	./gradlew build

push-java:
	./gradlew publish -P version=${VERSION} --info
