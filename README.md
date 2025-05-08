# Morris Health Service (MHS) Management System

## Authors
Abhinava Sai Tirunagari

## Project Description

This project is a **Java Swing** application integrated with a **MySQL** backend designed to manage a hospital system called Morris Health Services (MHS). It enables administrative operations, patient management, and insightful reporting via an intuitive graphical interface.

Whole project is implemented as a Layered Architecture with the following layers:
- **Presentation Layer**(design): This is the user interface layer which is implemented using Java Swing.
- **Business Logic Layer**(Service): This layer contains the core logic of the application and is implemented using Java
- **Data Access Layer**: This layer is responsible for interacting with the database and is implemented using Java
- **Data Access Object (DAO)**: This layer is responsible for encapsulating the database operations and is implemented using Java.
 

### Key Modules:
- **Employee and Facility Management**  
- **Patient Management**  
- **Management and Reporting**

## Technologies Used
- Java (Swing GUI)
- MySQL
- JDBC (for database connection)

## Features

### Employee and Facility Management
- Insert, update, and view employees and facility records
- GUI forms dynamically adjust based on user actions
- Update operations require a unique ID

### Patient Management
- Insert, update, and view patient records
- View-only access for patient invoices
- Dropdown-driven interaction for ease of use

### Management and Reporting
- Daily revenue by facility
- Appointments list for a selected physician and date
- Facility-based reports for a given date range
- Top 5 highest revenue days per month
- Average daily revenue per insurance provider

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK)
- MySQL Server
- JDBC MySQL Connector JAR

### Database Setup

1. Create the database:
    ```sql
    CREATE DATABASE MHS;
    USE MHS;
    ```
2. Execute the SQL commands provided in the documentation to create and populate the tables. These include entities such as Employee, Facility, Patient, Insurance_Company, Invoice, and more, along with foreign key constraints for relational integrity.  
3. Add the MySQL JDBC Connector to your classpath.

## Running the Application

### Option 1: Using an IDE

- Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

- Set up the project with the MySQL connector JAR.

- Run the DashboardDesign.java file.

### Option 2: Using the Terminal
```
javac -cp ".;mysql-connector-java-X.X.X.jar" *.java
java -cp ".;mysql-connector-java-X.X.X.jar" DashboardDesign
```
Replace X.X.X with the actual version of your JDBC driver.

## Database Schema Overview
This system uses a normalized relational schema that includes the following major tables:

- Employee, Doctor, Nurse, Admin, Other_HCP

- Facility, Outpatient_Surgery, Office

- Patient, Insurance_Company

- Invoice, Invoice_Detail

- Makes_Appointment, Treats

## Project Structure 

- DashboardDesign.java: Main GUI interface

- InsertDesign.java, UpdateDesign.java, ViewDesign.java: Interfaces for data operations

- InsertService.java, UpdateService.java, ViewService.java: Business logic layer

- InsertDAO.java, UpdateDAO.java, ViewDAO.java: Data access layer

- ManagementAndReportingDesign.java: GUI for reports

- QueryInputDesign.java: Input forms for custom queries

## Challenges Faced

- Handling JDBC driver compatibility and database connection setup

- Creating flexible GUI forms for dynamic data entry across multiple tables

- Ensuring correct syntax translation from SQL to Java Strings

- Managing foreign key relationships in data manipulation tasks

## Conclusion

The MHS Management System provides a robust foundation for healthcare facility administration. Its modular structure supports scalable development and real-world operational scenarios in healthcare data systems. With user-friendly forms and extensive reporting tools, this project can serve both educational and practical use cases in health informatics.

