#!/usr/bin/env bash
# Environment Variables
# HUB_HOST
# BROWSER
# MODULE

echo "Checking if hub is ready - $HUB_HOST"

while [ "$( curl -s http://$HUB_HOST:4445/wd/hub/status | jq -r .value.ready )" != "true" ]
do
	sleep 1
done

# start the java command
java -cp selenium-docker.jar:selenium-docker-tests.jar:libs/* -DHOST=$HOST -DHUB_HOST=$HUB_HOST org.testng.TestNG testng.xml
	
	