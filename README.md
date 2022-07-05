# Doordash-project-demo

## Requirements
1. Java - 11.x

2. Maven - 3.x.x

3. MySQL - 8.x.x

## Steps to setup

### 1. Clone the application
```
git clone https://github.com/mpison/doordash-project-demo.git
```

### 2. Create Mysql database
#### for dev db
```
create database doordashDB
```
#### for test db
```
create database doordashDB-test
```

### 3. Change mysql username and password as per your installation

For dev environment open src/main/resources/application.properties

For test environment open src/test/resources/application-test-containers.properties

change spring.datasource.username and spring.datasource.password as per your mysql installation

### 4. Build and run the app using maven

cd data-service
mvn package
java -jar target/data-service-0.0.1-SNAPSHOT.jar

You can also run the app without packaging it using -

mvn spring-boot:run

### 5. Test using POSTMAN or following CURL command

```
curl -X POST \
  http://localhost:8080/data-app/api/phone-numbers \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 0f8a4f54-36f8-fe88-e665-5a7e21a69c96' \
  -d '{
	"raw_phone_numbers" : "(Home) 415-415-4155 (Cell) 415-514-5145"
}'
```
