package com.example.rental.service;

import com.example.rental.CarLoan;
import com.example.rental.repository.CarRentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CarRentalServiceImpl implements CarRentalService{

    @Autowired
    private CarRentalRepository carRentalRepository;
    @Override
    public List <CarLoan> getLoans() {
        return carRentalRepository.findAll();
    }

    @Override
    public Boolean checkCarAvailability(String car_name, java.sql.Date start_date, java.sql.Date return_date ) {
        return carRentalRepository.checkAvailability(car_name, start_date, return_date);
    }

    @Override
    public void save(CarLoan carLoan) {
        carRentalRepository.save(carLoan);
    }

    @Override
    public double getRevenue() throws SQLException {
        return carRentalRepository.getRevenue();
    }
}
