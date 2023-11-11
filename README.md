
# Overview

This is a web application for creating, viewing and signing petitions developed in Java using Spring Boot. DevOps automation is incorporated using Jenkins and a Dockerfile is provided for deploying the application on a Tomcat server container.

# Features

The main features/pages of the application are as follows:
- **Home:** The home page, contains links to the create, view and search features
- **View all:** Show all petitions in the system
- **View one/sign:** Show the details (including signatures) of a single petition and add a signature
- **Search:** Search for petition titles and display the results
- **Create:** Create a new peition

# Technologies

The application is primarily developed in Java and uses the following technologies and tools:

- **Maven:** Build automation, resolving dependencies
- **Spring Boot:** An addition to the Java Spring framework that facilitates application developemnt 
- **Spring Data JDBC:** A component of the Spring framework that facilitates data storage and access using e.g. SQL databases
- **H2 SQL Database:** An in-memory relational SQL database in Java for application development and testing. 
- **Thymeleaf:** A view technology for rendering dynamic content in web applications
- **Docker:** A containerisation technology which is used to deploy the application using Tomcat server 
- **Jenkins:** An automation server that facilitates continuous integration/continuous delivery (CI/CD) by automating the steps in the development process (e.g. building, testing and deploying an application)
- **Bootstrap:** CSS framework

Further details about the application are available in the accompanying report.

# Quickstart

To run the application on the localhost:
- Install Java version 17
- Install Maven and ensure the ``mvn`` command is available on a terminal
- Run ``mvn spring-boot:run``
- Navigate to ``http://localhost:8080`` in a web browser

# Setup and Configuration

The setup and configuration of the application is described in the following sections. It is assumed that the following are available on the system:
- Java version 17
- Maven, configured so that it is available on the terminal using the ``mvn`` command
- Docker is installed
- A Jenkins server is running for executing the CI/CD pipline. The Jenkins server can be running on a separate system to the development system.

## Build/Run/Package

Maven is used as the build tool for the application and is configured using the ``pom.xml`` file. The following Maven plugins/goals can be used for building and running the application:

- ``mvn clean:clean``: Remove any files associated with a previous build
- ``mvn dependency:copy-dependencies``: Copy any dependencies declared in the ``pom.xml`` file to the target directory
- ``mvn compiler:compile``: Compile the Java files
- ``mvn test``: Executes the tests in /src/test/java/org/ruairispetitions directory
- ``mvn package``: package the application to .war file for subsequent deployment to a server. The generated .war file is saved in ``/target/ruairispetitions.war``
- ``mvn spring-boot:run``: Run the application in the embedded Spring HTTP server for development and testing purposes. The application is available by default on ``http://localhost:8080/``

## Database

Data for the application is managed by an in-memory SQL database (H2). Since the database is in-memory, the data are not persisted when the application is stopped.

This H2 database facilitates development using a SQL environment but without the need to setup an on-disk SQL database using e.g. PostgreSQL. The application can be easily migrated to use an on-disk database if required. 

The database is initialised using the SQL script (src/main/resources/schema.sql) when the application is started. Some configuration options for the H2 database are in ``src/main/resources/application.properties``.

## Deployment on Tomcat Server using Docker

The .war package created using mvn package can be deployed on a Tomcat server. For simplicity, the Tomcat server can be created as a Docker image and executed as a container. The .war file is copied to the Tomcat webapps directory on Docker image (``/usr/local/tomcat/webapps/``). When the container is run, the application is served by Tomcat.

The following steps can be used to deploy the application:

- Build .war file first using ``mvn package``
- Build a docker image comprising of the Tomcat base image and the application .war file ``docker build -f Dockerfile -t ruairispetitions .``. See the ``Dockerfile`` for details of how the image is built.
- Run a docker container using the generated image: ``docker run --name ruairispetitions_container -p 9090:8080 --detach ruairispetitions:latest``

## CI/CD Pipeline Using Jenkins

A CI/CD pipeline for the application can be implemented using Jenkins and the supplied Jenkinsfile. A Github webhook can be configured to automatically trigger builds on a remote server running Jenkins when commits are pushed to the github repository

This pipeline consists of the following steps:

- **Clone:** Clones the gihub repository
- **Build:** Cleans the directory, copies the dependencies listed in pom.xml and compiled the java source code.
- **Test:** Executes the automated tests defined in ``/src/test/java/org/ruairispetitions/``
- **Package:** Creates a .war file for subsequent deployment on a Tomcat server
- **Deploy:** An optional step which deploys the generated .war file on a dockerised Tomcat server. The application is then available on the host machine at port 9090. This step must be manually approved in the Jenkins UI during the build (subject to a 5 minute timeout)
- **Post Success Actions:** The generated artifact (ruairispetitions.war) is archived to the jenkins working directory following a successful build.

 To run the pipeline, a Jenkins server must be available and a new task should be configured with the following options:
 - **Pipeline Section:** Select "Pipeline script from SCM"
 - **Repository URL:** https://github.com/ruairin/ruairispetitions.git
 - **Branches to Build:** Specify the branch (e.g. */dev)
 - **Script Path:** Jenkinsfile
 - **Build Triggers (*):** Select "GitHub hook trigger for GITScm polling"

 (*): Only needed if triggering builds using a GitHub webhook is required

# Source Code Structure

The source code structure showing the most important files is shown below

```
Application
│   - Jenkinsfile
│   - Dockerfile
│   - README.md
│   - pom.xml
│
├─── /src/main/java/org/ruairispetitions/
|         RuairispetitionsApplication.java
│         /controller/
|           - PetitionsController.java
│         /model/
|           - Petition.java
│         /repository/
|           - PetitionsRepository.java
|
├─── /src/main/resources/
|       application.properties
|       schema.sql
│       /static/
|           - index.html
│       /templates/
|           - *.html
|
└─── /src/test/java/org/ruairispetitions/
          - RuairispetitionsApplicationTests.java
          - PetitionsControllerTests.java

```

| Directory | File | Description |
| --- | --- | --- |
| / | pom.xml | The configuration file for Maven (project dependencies and build configuration) |
| / | Dockerfile | The Docker file for creating a Tomcat server image |
| / | Jenkinsfile | Defines the stages to be executed as part of a Jenkins build |
| / | README.md | This readme file |
| /src/main/java/org/ruairispetitions/controller/ | PetitionsController.java | The main backend logic of the application, including routing, database queries and validating user inputs |
| /src/test/java/org/ruairispetitions/ | PetitionsControllerTests.java | Unit tests for the controller methods |
| /src/main/resources/ | schema.sql | The initial schema and test data for the database |
| /src/main/resources/ | application.properties | Various application configuration, mainly related to the H2 database |
| /src/main/resources/static/ | index.html | The home page of application |
| /src/main/resources/templates/ | *.html | Thymeleaf templates for rendering dynamic content |

