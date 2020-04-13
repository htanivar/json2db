# What is expected to be done via this project

Project is expected to read json files from FTP Server and load into the database via 
Rest controller end point.

## How to run the project in your machine
### Prerequisite
1. Gradle Installed in local (I know wrapper will be good)
1. Acceptance to run the project with default local profile

### ISSUE in Execution
1. Unable to run *_gradle build_*
1. default jsonPath in DBLoadController.java needs to be fixed[work around run localtest]

### Project Execution Steps
1. Get the source code in your local machine
1. Navigate to the location of the build.gradle file
1. Run in local profile (H2 Database): gradle bootRun --args='--spring.profiles.active=local'
1. Run in dev profile (mariaDB): gradle bootRun --args='--spring.profiles.active=dev,--spring.datasource.password=' (you need to configure your mariaDb password in application-dev.properties file)
    a.  Unable to execute just with command line...need to update the application-dev.properties file in the execution machine

### Project Navigation (Make sure port 8090 is not used)
1.  [Swagger UI](localhost:8090/swagger-ui.html) --> localhost:8090/swagger-ui.html
1.  [H2 Database](localhost:8090/json2db) --> localhost:8090/json2db

### How to run the project with required profile (with Gradle)
SPRING_PROFILES_ACTIVE=local gradle clean bootRun


## Project Development Tasks
1. Create a Spring-Boot project   --> **Done**
1. Create the Domain object --> **Done**
1. Read the a file from test/resources/jsonPath/test.json as Json String --> **Done**
1. Map the Json string to Domain Object and Save the Domain Object to Database --> **Done**
1. Add controller to read static files and load into DB  --> **Done**
1. Add Swagger to project
