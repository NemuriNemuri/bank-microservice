# Bank Microservice - Java 8 Spring Boot Microservice

## Tech Stack
- Java 8
- Spring Boot 2.6.15
- H2 In-Memory Database
- Spring Data JPA
- Maven

## How to Run

1. Clone repository
2. Run:

mvn clean install
mvn spring-boot:run

3. Access H2 Console:
http://localhost:8080/h2-console

JDBC URL:
jdbc:h2:mem:testdb

## APIs

POST /api/v1/account  
GET /api/v1/account/{customerNumber}
