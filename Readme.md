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

### Send Message API Request 

```
{
  "sender": "james",
  "recipient": "kranthi",
  "shortMessage": "string",
}

curl -X POST "http://localhost:8080/api/message" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"recipient\": \"string\", \"sender\": \"string\", \"shortMessage\": \"string\",}"
```
When Success , it returns ID of the message and http status code 200

### getMessagesForSender
```
curl -X GET "http://localhost:8080/api/message/kranthi" -H "accept: */*"

or 

curl -X GET "http://localhost:8080/api/message/kranthi?numberOfDays=30" -H "accept: */*"
```
numberOfDays -> Is optional parameter 


### getMessageBetweenSenderAndReceiver
```
curl -X GET "http://localhost:8080/api/message/kranthi/sam" -H "accept: */*""

or 

curl -X GET "http://localhost:8080/api/message/kranthi/sam?numberOfDays=30" -H "accept: */*""
```
numberOfDays -> Is optional parameter 


when there are no messages for the specific user, Service will return http status code as 204


This APP is also available on heroku. Please reach me if the following url is not showing up.  

https://simple-chat-app-kk.herokuapp.com/swagger-ui.html
