# Fuse Archetype - Camel Workshop

## Description

Fuse Archetype - Camel Workshop is a maven archetype allowing to generate a maven project structure
to develop a POC during a workshop.

Download project from Git and build it : mvn install

## How to use it

- Simply execute the following command in a shell environment

    mvn archetype:generate -DarchetypeGroupId=org.fusesource.archetypes -DarchetypeArtifactId=fuse-workshop-archetype -DarchetypeVersion=1.0-SNAPSHOT -DgroupId=org.fusesource.workshop -DartifactId=poc -Dversion=1.0

- Go to poc directory and run a mvn clean install.
- After that you can launch Camel in standalone mode or deploy it in Fuse ESB (more info in README file of the project created)