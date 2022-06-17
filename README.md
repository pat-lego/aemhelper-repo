# Support Helpers Repository

This is a repository to contain some helper functions in order to better test and debug AEMaaCS.


Note: This project is only supported on AEMaaCS!

## Email Tester

There is a Vue 3 app located at `/content/support/email.html` it will send a JSON post request to `/content/support/email.json` and can be used to validate an Advanced Networking configuration.

# How to build

mvn clean install

# Project Archetype Command
mvn -B org.apache.maven.plugins:maven-archetype-plugin:3.2.1:generate \
 -D archetypeGroupId=com.adobe.aem \
 -D archetypeArtifactId=aem-project-archetype \
 -D archetypeVersion=36 \
 -D appTitle="AEM Support Helpers" \
 -D appId="supporthelpers" \
 -D groupId="com.adobe.aem" \
 -D artifactId="support-helpers"
