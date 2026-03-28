🚀 Key Features

Smart Prioritization: Tasks are managed using a custom Priority Queue implementation (Java) to ensure efficient task scheduling.



Full-Stack Integration: A standalone Spring Boot 3 server handling business logic and user data.



RESTful API: Seamless communication between the JavaFX client and the backend.



Secure Authentication: User passwords are protected using BCrypt hashing for industry-standard security.



NoSQL Database: Persistent storage using MongoDB Atlas, enabling scalable data management.



Responsive UI: Built with JavaFX and CSS for a clean, desktop-first user experience.



🛠 Tech Stack

Language: Java 25 (JDK 25)



Frameworks: Spring Boot 3, JavaFX 21+



Database: MongoDB



Security: Spring Security \& BCrypt



Dependencies: GSON (for JSON parsing)



⚙️ Setup \& Installation

Prerequisites

To run this project, you must have the following installed:



JDK 25 (Ensure JAVA\_HOME is set).



JavaFX SDK (Required for the frontend).



GSON Library (.jar file for JSON handling).



1\. Backend Setup (Spring Boot)

Navigate to the TaskCalendar\_Server directory.



Update the application.properties with your MongoDB Connection String.



Run the server using Maven:



Bash

mvn spring-boot:run

2\. Frontend Setup (JavaFX)

Open the project in your IDE (Cursor/IntelliJ/Eclipse).



Add the JavaFX and GSON libraries to your project structure (Project Structure -> Libraries).



Add the following VM options to your Run Configuration:



Plaintext

\--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml

Launch the Main application.



🧬 Project Evolution

This project evolved from a university assignment focusing on Object-Oriented Programming (OOP) and Data Structures into a production-ready architecture featuring:



Dependency Injection (Spring Boot).



Asynchronous API calls to prevent UI freezing.



Cloud Persistence instead of local file storage.

