# Support Helpers Repository

This is a repository to contain some helper functions in order to better test and debug AEMaaCS.


Note: This project is only supported on AEMaaCS!

## Email Tester

There is a Vue 3 app located at `/content/support/email.html` it will send a JSON post request to `/content/support/email.json` and can be used to validate an Advanced Networking configuration.

Note: that you will need to allow a POST request from the dispatcher if you are testing this from the Publisher. If you are not testing this from the publisher then there is no need to add this as there is no Dispatcher in front of the Author in AEMaaCS.

Setup:

1. Make sure to configure the Day CQ Mail Service with the appropriate values
2. Configure Advanced Networking for your Program/Environment
3. Change the logger to append to the error.log 

I do not recommend to install this in Stage/Prod environments.

# How to build

mvn clean install

# How to deploy

Create a local reposiory in your Cloud Manager repo and add the all ZIP of this build in the local repository and add it to your all project as an embed.
# Project Archetype Command
mvn -B org.apache.maven.plugins:maven-archetype-plugin:3.2.1:generate \
 -D archetypeGroupId=com.adobe.aem \
 -D archetypeArtifactId=aem-project-archetype \
 -D archetypeVersion=36 \
 -D appTitle="AEM Support Helpers" \
 -D appId="supporthelpers" \
 -D groupId="com.adobe.aem" \
 -D artifactId="support-helpers"
