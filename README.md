# BookingGo Technical Test

## Setup
Gradle version 6 has been used to manage this project

Java version 8 has been used to write the application

There is a dependency on Spring Boot version 2 which handles the API

Development was done in intelliJ 2019.3 Ultimate version as it provides
spring support

I have also tested running the application in a terminal window outside 
of intelliJ which worked using gradle version 6

The application can be built with the command 

    ./gradlew build
    
## Running The Application

### Part 1

Console application to print the search results for Dave's Taxis

Please run using the command

    ./gradlew bootRun --args "pickup, dropoff"
    
    for example 
    
    ./gradlew bootRun --args "3.410632,-2.157533 3.410632,-2.157533"

Console application to filter by number of passengers

Please run using the command

    ./gradlew bootRun --args "pickup, dropoff passengers"
    
    for example 
    
    ./gradlew bootRun --args "3.410632,-2.157533 3.410632,-2.157533 4"
    
### Part 2

To launch API please use the command

    ./gradlew bootRun
    
This will launch a spring boot application running on http://localhost:8080

The API has two routes one for dave's taxi and one for all APIs

To make a request to Dave's taxi's use a command such as the following

    http://localhost:8080/dave?pickup=3.410632,-2.157533&dropoff=3.410632,-2.157533
  
    This makes use of the dave route and expects pickup and drofoff to be supplied
    
To make a request to all APIs use a command such as the following

    http://localhost:8080/taxi?pickup=3.410632,-2.157533&dropoff=3.410632,-2.157533&passengers=6
    
    Note this one needs pickup, dropoff and passengers to be supplied
    
