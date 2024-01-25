# Testing Apache Camel in Spring Boot

## Project description
This project contains some dummy code that creates a Apache Camel Route and tests it in different ways.

It exposes some confusing issues that one need to know about when trying to test for Camel.

The code is build in several stages and the _master_ branch holds the whole completed code. Each chapter/step in the blog is found in its own branch with a number to make it easer. Each branch builds on the previous.

### 0. Run with Random Messages 

Start the application usiing `mvn spring-boot:run`.

Notice that random messages, plain and json, are being sent and logged.
You can also go to http://localhost:8080/actuator/hawtio/camel/contexts and see the traffic (but it is a bit buggy for Spring 3.x).

### 1. Mock the endpoints

### 2. Turn off the internal ActiveMQ

### 3. WaveBy
