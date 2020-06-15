# Publish & Consume Events

This project is built with two applications in spring boot which receives & consumes events and communicates to each other with Apache Kafka via Google Protocol Buffers

## Microservices

  - Publisher Application using Spring Boot
  - Consumer Application using Spring Boot

## Technologies Used

  - Java 8
  - Apache Kafka
  - Google ProtoBuf
  - Docker

## Overview

A Publisher Application is built using Spring Boot Framework and uses Apache Kafka to publish events.

Apache Kafka is a distributed streaming platform with capabilities such as publishing and subscribing to a stream of records, storing the records in a fault tolerant way.

Publisher App has a POST API which accepts JSON Event Data and sends it to an async service which internally publishes to a programatically created Kafka Topic.

The specification of Event data,

```
{
   "timestamp" : long,
   "userId" : int,
   "event" : string
}
```

A Consumer Application is built using Spring Boot Framework which listens to the Kafka Topic and retrieves the event message and writes to a log file in disk.

Google Protocol Buffers is used which is a language and platform neutral mechanism for serialization and deserialization of structured data utilized in both producer and consumer applications.

The schema of the event message is defined in .proto files. We would require a compiler to convert language neutral content to a Java Code.

Sample .proto file,

```proto
syntax = "proto3";

option java_multiple_files = true;
option java_package = "com.protobuf.model";

message EventMessage {
    int64 timestamp = 1;
    int32 userId = 2;
    string event = 3;
}

message StreamMessage {
    oneof event {
        EventMessage message = 1;
    }
}
```

With the help of maven plugin `protoc-jar:run` we will be able to compile and generate the source files.

Message Serializers and Deserializers are specified in both publisher and consumer applications respectively to send and transform the protocol buffer messages.

# Steps to Run the Application

## Prerequisites

  - Java 8+
  - Docker
  - Docker Compose

## Build Publisher and Consumer applications

Navigate to `Event_Publisher\publisher` and `Event_Listener\consumer` respectively and run,

```bash
mvn clean protoc-jar:run install
```

This will compile the defined `message.proto` file and downloads necessary dependencies for the project.

## Start Zookeeper and Kafka Containers

Navigate to `docker-compose.yml` file under Event_Publisher\publisher and run,

```bash
docker-compose up
```

This will start Zookeeper and Kafka containers in `9092` and `2181` bound to local ports respectively

## Run Publisher Application

Navigate to `Event_Publisher\publisher\target` and run the generated jar,

```bash
java -jar publisher-app.jar
```

This will start the publisher application in 8080.

## Run Consumer Application

Navigate to `Event_Listener\consumer\target` and run the generated jar,

```bash
java -jar consumer-app.jar -Dlogging.config=.\Event_Listener\consumer\config\logback.xml
```

## Send Event Request

```bash
curl --location --request POST 'http://localhost:8080/api/events' \
--header 'Content-Type: application/json' \
--data-raw '{
    "timestamp": 1518609008,
    "userId": 1123,
    "event": "2 hours of downtime occured due to the release of version 1.0.5 of the system"
}'
```

## Desired output

The received events are logged under `Event_Listener\consumer\target\logs\events.log` file


