# FloorPlan App 

This java app is created using Spring Boot and h2 in-memory database and AWS3. It has following endpoints 

### Project

1. create
2. patch/update
3. get
4. delete 

### Flooplan 
1. create
2. patch/update
3. get
4. delete 

Tech Stack 
* Spring boot Rest
* Spring data/Spring JPA 
* H2 Database 
* Heroku 
* AWS S3 

# Database Modeling 
 
For the simplicity of the app, I have created an object to store the messages. column sentDate will be helpful to test the old messages. 

![Data Model](https://user-images.githubusercontent.com/9857819/130498105-b5d81252-8fe6-4bf3-a82c-07976f2803e3.png)

During the boot time, Project and floorplan table will be created.  Create statements can be found at 

[data.sql](https://github.com/kranthides/floorplan-app/blob/main/src/main/resources/data.sql).


# Architecture - Current State

![Design Document](https://user-images.githubusercontent.com/9857819/130499873-ea98bd7f-8706-4cbc-ba46-a73d2a2864e3.png)


**Limitations of the current design**
* If the database is down for maintenance or for other reasons, data will be lost. 
* I am using the relational database for the simiplicity, For this APP NoSQL / MongoDB will be more ideal. 

# Future state 
![Future State](https://user-images.githubusercontent.com/9857819/130499571-80aa3a4f-abe7-45b4-8cc2-d5e4de09b289.png)

* User Services will help to create the users who maintains the projects and floorplans 
* Authentication Service helps to secure the services. 
* Distributed Cache is critical to load the images/data faster/ 
* Pubsub/rabbitmq queues are helpful to handle the messages asynchronously. 


# Tech Debt 
* Authentication and Autherization 
* CI/CD pipeline using Docker/ Kubernates 
* Unit test cases 

# Steps to build and run the job 

git clone https://github.com/kranthides/floorplan-app.git
cd floorplan-app 
mvn clean package 
java -jar target/floorplan-app-1.0.jar --jasypt.encryptor.password=

http://localhost:8080/swagger-ui.html

# Project 
### Create Project Request 

```
 curl -X POST "https://floorplan-app-kk.herokuapp.com/api/project" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"projet1\"}"

```
When Success , it returns ID of the message and http status code 200

### Get Project By Id
```
curl -X GET "https://floorplan-app-kk.herokuapp.com/api/project/1" -H "accept: */*"

```

### Update Project 
```
curl -X PATCH "https://floorplan-app-kk.herokuapp.com/api/project" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"id\": 1, \"name\": \"Project1_updated\"}"

```

### Delete Project 
```
curl -X DELETE "https://floorplan-app-kk.herokuapp.com/api/project/1" -H "accept: */*"

```
 
 
# FloorPlan  
### Create FloorPlan Request 

```
curl -X POST "http://localhost:8080/api/floorplan?name=FloorPlan-1&projectId=1" -H "accept: */*" -H "Content-Type: multipart/form-data" -F "file=@floorplan2.jpg;type=image/jpeg"

```
When Success , it returns ID of the message and http status code 200

### Get Project By Id
```
curl -X GET "http://localhost:8080/api/floorplan/4" -H "accept: */*"

```

### Update Project 
```
curl -X PATCH "http://localhost:8080/api/floorplan?id=4&name=Floorplan-4-Updated&projectId=1" -H "accept: */*" -H "Content-Type: multipart/form-data" -d {"file":{}}

```

### Delete Project 
```
curl -X DELETE "http://localhost:8080/api/floorplan/4" -H "accept: */*"

``` 
This APP is also available on heroku. Please reach me if the following url is not showing up.  

https://floorplan-app-kk.herokuapp.com/swagger-ui.html