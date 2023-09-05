CREATE TABLE car_loans (
    loan_id SERIAL PRIMARY KEY,
    customer_age INT NOT NULL,
    car_name VARCHAR(255) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    start_date TIMESTAMP,
    return_date TIMESTAMP,
    estimated_price DOUBLE PRECISION
);