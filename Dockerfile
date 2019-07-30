FROM openjdk:8u191-jre-alpine3.8
#create working directory
WORKDIR /usr/share/Code
ADD target/selenium-docker.jar selenium-docker.jar
ADD target/selenium-docker-tests.jar selenium-docker-tests.jar
ADD target/libs libs
ADD src/test/resources/testng.xml testng.xml
ENTRYPOINT java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* -DHOST=$HOST -DHUB_HOST=$HUB_HOST org.testng.TestNG testng.xml
