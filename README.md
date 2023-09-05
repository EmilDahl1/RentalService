
# Fortnox Car rental

Code regarding fortnox code test "Miniature car rental app".

A user can submit a rental form to the database.
The admin can see all submitted rental applications and a calculated estimated revenue.

Note: Cars cannot be double-booked, this pretends that the car dealership has exactly one of each car. Further implementation could see this changed to keep a count of available cars of each brand.

Further development should also implement user registration as well as a function for the admin to confirm the booking by sending something like and email from the app. 

Other than this all specified practical demands from the task description are met.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Installation](#installation)
3. [Backend Setup](#backend-setup)
4. [Database Setup](#database-setup)
5. [Running the Project](#running-the-project)
6. [Running Tests](#running-tests)

## Prerequisites

- Node.js (v14+)
- npm (v6+)
- Java 20 or equivalent
- Maven
- PostgreSQL
- Database interaction with DBeaver (optional)

## Installation

1. **Clone the repository**

    ```
    git clone https://github.com/your-username/your-project-name.git
    ```

2. **Navigate to the project directory**

    ```
    cd your-project-name
    ```

3. **Install Frontend Dependencies**

    ```
    npm install
    ```

## Backend Setup

1. **Navigate to the backend folder**

    ```
    cd backend-folder-name
    ```

2. **Install Backend Dependencies**

    Make sure you have Maven installed. Run:

    ```
    mvn install
    ```

3. **Setup Configuration**

    Navigate to the class CarRentalRepositoryImpl and change database connectivity variables to that of local database. (Username, password, url)

   
## Database Setup

1. **Start PostgreSQL Server**

    Make sure PostgreSQL is running on your machine. If not, you can start it with:

    ```
    pg_ctl -D /usr/local/var/postgres start
    ```

2. **Create Table**

    Use the `data.sql` file provided in the repository to create necessary tables.


## Running the Project

1. **Run Backend**

    ```
    mvn spring-boot:run OR run the class RentalApplication (run tests first)
    ```

2. **Run Frontend**

    ```
    npm start
    ```

## Running Tests

> Note: Tests in the backend should be run on an empty database as some checks are hardcoded.

1. **Backend Tests**

    ```
    Run the test class RentalApplicationTests
    ```




