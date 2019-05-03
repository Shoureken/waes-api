WAES Test - Assignment Scalable Web
==============

It is an REST API that accepts two JSON Base64 encoded, store and compare differences.

# What I have used

- Java 8 (Language)
- Spring Boot (Framework)
- H2 (Database)
- Flyway (Database Migration)
- Swagger (Documentation)
- JUnit (Tests)

## Compile Requirements

- JDK 1.8
- Maven 3.3

## Compile and Run

 In the project folder run this command to compile and start the application, this also start an H2 database in memory and run the migrations
 > mvn clean spring-boot:run
 
## API Documentation (Swagger)

 Running application you can access a build-in documentation
- [Swagger](http://localhost:8080/swagger-ui.html)

## H2 - Database console

 Running application you can access a build-in H2 Console, using the url "jdbc:h2:mem:testdb"
- [Database console](http://localhost:8080/h2-console/) 

## Run Tests
To run the tests, you just have to run this command in the project folder
> mvn test

# API Instructions

### Store Data
To store data you must to provide a JSON base64 string as payload of a POST request
>POST: http://localhost:8080/v1/diff/{id}/{side}
* "id" - String - You can choose any string 
* "side" - String - must be "left" or "right"
* payload has to be a JSON encoded

For exemplo:
>"{"data":10}" would be "eyJkYXRhIjoxMH0="

Possible API responses are:
* 200 - If it succeed to store
* 400 - If payload is invalid

### Consult Diif
To consult the diff result you must provide some data on both sides (left and right) on the same ID
>GET http://localhost:8080/v1/diff/{ID}
* "id" - String - ID that you used to store the data

This will return a JSON containing the result, each can be:
* DIFFERENT_LENGTH - Data has different sizes  
* EQUAL - Date are exquily the same 
* NOT_EQUAL - Data has the same size, but has differences, in this case theses parts will be presented

# Project Info

The project follow a well-know structure of microservice, using REST and MVC. 
Since it is a demonstration project I kept it simple, divided in tree layer basically
* REST Controller - That receive that requests
* Business - Holds the application logic
* Persistency - The layer that stores and retrieve from database 

## Decisions
1. I used an in memory database because it is a demonstration, this way wont be necessary a 
pre-installed database. But remember that it is very easy to switch to another database if necessary
2. Flyway was used because is very easy to use and work out of the box with Spring
3. Swagger almost doesnt need justification, it is widely used to create API specifications


## Suggestions

1. The API could return what actually is different, not just offset end length
2. Today the diff process is synchronized with the request, If it receives a very large data or too 
many request, probably the requests will timeout. So the diff process should be asynchronous, maybe using
a process queue or even a callback endpoint where the application use push back the result to the clients