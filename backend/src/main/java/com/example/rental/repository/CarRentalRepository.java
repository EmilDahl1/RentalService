package com.example.rental.repository;

import com.example.rental.CarLoan;

import java.sql.SQLException;
import java.util.List;

public interface CarRentalRepository {

    List<CarLoan> findAll();
    CarLoan findById(int id);
    void save(CarLoan carLoan);

    boolean checkAvailability(String car_name, java.sql.Date start_date, java.sql.Date return_date);


    double getRevenue() throws SQLException;
}
