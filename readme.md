# AXA Java Challenge #

## Contents ##
    1. Requirements
    2. Setting Up
    3. Running Test Units
    4. Deploying
    5. Documentations
    6. Enhancement Updates
    7. About Me

## 1. Requirements ##
- Java 1.8 JDK & JRE
- Spring Boot 2.1.4.RELEASE
- IDE or Text Editor

## 2. Setting Up ##
1. Clone the repository
2. Install Dependencies: `mvn clean Install`
3. Run `mvn spring-boot:run` for starting the application (or use your IDE)

## 3. Running Test Units ##
- Run the test command: `mvn clean test`

## 4. Deploying ##
1. It's best a practice to run the test command first: `mvn clean test`
2. Build the application (.jar): `mvn clean package -DskipTests`
3. Locate the .jar file in *.\GitHub\java-challenge\target*

## 5. Documentations ##
- *Swagger UI* : http://localhost:8080/swagger-ui.html
- *Postman Doc*: https://documenter.getpostman.com/view/8446507/2sA3QwbUnd
- *H2 UI* : http://localhost:8080/h2-console

## 6. Enhancement Updates ##
1. Code Refactor
2. Bug Fixes
3. Added Client Credential Interceptor
4. Improved Swagger Documentation
5. Added DB Caching
6. Added Test Units
7. Added Custom ApiExceptions

## 7. About Me ##
こんいちは！、I am Albert V. De Leon Jr. I have been using Spring Boot for 4 years.
I used Spring Boot for 1 year in a Small Business (Laundry) Systems and 2 years and 10 months
for a large scale fintech application.