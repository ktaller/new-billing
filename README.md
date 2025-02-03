# Water Billing Rest Server

This is a simple rest server for water billing system. It is written in Java using Spring Boot framework. It uses MySQL as the database.

The server provides the following functionalities:

-   Customer management
-   Bill management
-   Payment management
-   Report generation

## Prerequisite

This project depends on the following:

-   [Java 17 JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
-   [PostgreSQL](https://www.postgresql.org/download/)
-   [Gradle](https://gradle.org/install/)

## How to run the server

1. Clone the repository
    ```bash
    git clone https://www.github.com/ktaller/water_billing.git
    ```
2. Change directory to the project folder
    ```bash
    cd water_billing
    ```
3. Build the project
    ```bash
    gradlew build # linux
    gradlew.bat build # windows
    ```
4. Run the server
    ```bash
    gradlew bootRun # linux
    gradlew.bat bootRun # windows
    ```
5. The server will be running on `localhost:9090`.

### Note

You will need to allow the port through the firewall. This will make the site be accessible by other devices in the network including the android client application. You can do this by running the following command:

```bash
sudo ufw allow 9090/tcp
sudo ufw reload
```

Port and database credentials can be changed in [application.properties](src/main/resources/application.properties) file.
# water_billing_be
