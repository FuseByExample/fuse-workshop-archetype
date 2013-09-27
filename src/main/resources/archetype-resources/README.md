# FUSESOURCE POC WORKSHOP PROJECT - V1.0

Author : Charles Moulliard
Created : 12/09/2011

## Introduction

The goal of this project is to provide in a maven project structure the archetypes required to build camel routes, test them and deploy the project
in Fuse ESB or simply runs the camel routes locally. The project exposes 4 Camel routes :

FILE

fromFileToQueue
fromQueueToPoJo

WEB SERVICE

fromWebServiceToWsQueue
fromWsQueueToPoJo

where the 2 first can be use to copy a file in a directory and consulting the result into the log of the console. The other group of camel routes
exposes a WebService that we can reach using SOAPUI.

The routes have been defined using Java DSL but the definition of the camel endpoints/URI and context is defined in the file META-INF/spring/camel-context.xml.
The project can be extended using Spring DSL and Transactional Camel routes can be developed. The camel-context-tx.xml will be used for that purpose (remove the comments
and extend it).

## Pre-requisite

* Maven 3.0.x
* JDK 6, 7
* SOAP UI

## Maven Modules

* parent : contain dependencies, properties definitions and common maven plugins used by the other maven module
* routes : camel routes with unit test.
* features : features file
* tests : integration tests (OSGI)

## Build

    mvn clean install

## Launch Camel

a) Standalone mode

* Execute the following command within routing module

    mvn camel:run

b) Deploy in JBoss Fuse

* Install features file

    addurl mvn:org.fusesource.workshop/features/1.0/xml/features

* Install Poc camel routes

    features:install poc-fuse-camel

c) Web Application Server

* Execute the following command within routing module

    mvn jetty:run

* To generate the WAR file execute the following command

    mvn compile war:war

## Test it (for standalone and Fuse ESB)

FILE

* Copy the file test/data/incidentId.txt into the directory fusesource/data

````text
    e.g. cp ~/Fuse/fuse-by-examples/fuse-workshop-archetype/src/main/resources/archetype-resources/routing/src/test/data/incidentId.txt fusesource/data/
````

* Check the result in the log

````text
    14:03:49,794 | INFO  | fusesource/data/ | fromFileToQueue                  | ?                                   ? | 54 - org.apache.camel.camel-core - 2.7.1.fuse-00-43 | >>> File received : 999
    14:03:50,000 | INFO  | fusesource/data/ | fromFileToQueue                  | ?                                   ? | 54 - org.apache.camel.camel-core - 2.7.1.fuse-00-43 | >>> DocumentId created
    14:03:50,187 | INFO  | usesource-input] | fromQueueToLog                   | ?                                   ? | 54 - org.apache.camel.camel-core - 2.7.1.fuse-00-43 | >>> DocumentResponse created
    14:03:50,205 | INFO  | usesource-input] | fromQueueToLog                   | ?                                   ? | 54 - org.apache.camel.camel-core - 2.7.1.fuse-00-43 | >>> Incident created : <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <ns2:documentResponse xmlns:ns2="http://service.fusesource.org">
        <incidentId>999</incidentId>
        <givenName>Fuse</givenName>
        <familyName>Source</familyName>
        <details>This is a big incident</details>
        <email>info@fusesource.org</email>
    </ns2:documentResponse>
````

WEBSERVICE

* Create a project in SOAPUI pointing to the following wsdl --> http://localhost:9090/cxf/service?wsdl
* Send the following request

````xml
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.fusesource.org">
       <soapenv:Header/>
       <soapenv:Body>
          <ser:documentId>
             <id>999</id>
          </ser:documentId>
       </soapenv:Body>
    </soapenv:Envelope>
````
OR using curl (copy/paste the soap request in a file soap-demo.txt)

curl http://localhost:9090/cxf/service --header "content-type: text/soap+xml; charset=utf-8" --data @soap-demo.txt


* Get this response

````xml
    <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
       <soap:Body>
          <ns2:documentResponse xmlns:ns2="http://service.fusesource.org">
             <incidentId>999</incidentId>
             <givenName>Fuse</givenName>
             <familyName>Source</familyName>
             <details>This is a big incident</details>
             <email>info@fusesource.org</email>
          </ns2:documentResponse>
       </soap:Body>
    </soap:Envelope>
````
* Check the log

````
    14:05:17,454 | INFO  | er[fusesource-ws | fromQueueToPoJo                  | ?                                   ? | 54 - org.apache.camel.camel-core - 2.7.1.fuse-00-43 | >>> Web Service Message : <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <ns2:documentId xmlns:ns2="http://service.fusesource.org">
        <id>999</id>
    </ns2:documentId>
    14:05:18,474 | INFO  | tenerContainer-1 | fromWebServiceToQueue            | ?                                   ? | 54 - org.apache.camel.camel-core - 2.7.1.fuse-00-43 | >>> WebService called and incident created : <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <ns2:documentResponse xmlns:ns2="http://service.fusesource.org">
        <incidentId>999</incidentId>
        <givenName>Fuse</givenName>
        <familyName>Source</familyName>
        <details>This is a big incident</details>
        <email>info@fusesource.org</email>
    </ns2:documentResponse>
````

