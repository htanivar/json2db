# What is expected to be done via this project

Project is expected to read json files from FTP Server and load into the database via 
Rest controller end point.

## How to run the project in your machine
### Prerequisite
1. Gradle Installed in local (I know wrapper will be good)
1. Acceptance to run the project with default local profile

### ISSUE in Execution
1. Unable to run *_WRONG gradle build_*

### Project Execution Steps
1. Get the source code in your local machine
1. Navigate to the location of the build.gradle file
1. Run: gradle bootRun --args='--spring.profiles.active=local

### Project Navigation (Make sure port 8090 is not used)
1.  [Swagger UI](localhost:8090/swagger-ui.html) --> localhost:8090/swagger-ui.html
1.  [H2 Database](localhost:8090/json2db) --> localhost:8090/json2db



## Project Development Tasks
1. Create a Spring-Boot project   --> **Done**
1. Create the Domain object --> **Done**
1. Read the a file from test/resources/jsonPath/test.json as Json String --> **Done**
1. Map the Json string to Domain Object and Save the Domain Object to Database --> **Done**
1. Add controller to read static files and load into DB  --> **Done**
1. Add Swagger to project
