# How To Use the System

## Clone and Build
In IntelliJ, clone this repository as a new project.

    File > New > Project From Version Control > URL:<this repo>

Add a new Maven configuration with the following command line:

    clean compile war:war tomcat7:run
    
Build project.

## Open Web-App on Browser
Open any browser and open http://localhost:8080/webapp_1

## Using the System
### Sign Up
Fill in user information (DO NOT USE YOUR ACTUAL SOCIAL SECURITY NUMBER)

### Create | Edit | Cancel Reservations
Fill in reservation information.

Information MUST BE IN THE CORRECT FORMAT (the system does not check for invalid inputs). Use the following format.

    room: AOB | CAS | FNA | HBB | IBD | IBS | MWC | RND | RTE | TAA
    checkIn: YYYY-MM-DD
    checkOut: YYYY-MM-DD
    creditCardNumber: 1 | 2 | 3 | 4 | 5
    adults: int
    kids: int
 
### Admin View
Go to admin view to see monthly revenue for each room per month.
 
    user: riley
    password: wartenberg