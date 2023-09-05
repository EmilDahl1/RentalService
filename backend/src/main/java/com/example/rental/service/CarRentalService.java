package com.example.rental.service;

import com.example.rental.CarLoan;

import java.sql.SQLException;
import java.util.List;

public interface CarRentalService {

    List <CarLoan> getLoans();

    Boolean checkCarAvailability(String car_name, java.sql.Date start_date, java.sql.Date return_date);

    void save(CarLoan carloan);

    double getRevenue() throws SQLException;
}
