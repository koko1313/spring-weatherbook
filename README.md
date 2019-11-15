# WeatherBook

Социална мрежа за споделяне на времето в различните градове, направена на Java Spring

### Creating project with Spring Initializr

https://start.spring.io/

Единствена промяна в предефинираната конфигурация е в

Options -> Packaging -> War

В Dependencies -> Search dependencies to add, добавяме:

    Spring web

    Spring Boot DevTools

    Spring Data JPA

    Spring Boot Actuator

    H2 Database


Generate -> Save and extract the zip file

Close existing projects in eclipse

Go to File -> Import -> Maven -> Existing Maven Projects ->

select the extracted directory and click Finish

Right click on project -> Run as -> Maven Install

Then again right click on project -> Run as -> Java Application

The server should run by default on localhost:**8080** 

but we changed it in Java Resources > src/main/resources > application.properties to **9080**