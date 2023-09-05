package com.example.rental.repository;

import com.example.rental.CarLoan;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRentalRepositoryImpl implements CarRentalRepository {

    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String user = "postgres";
    private String password = "postgres";
    @Override
    public List<CarLoan> findAll() {
        List<CarLoan> carLoans = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM car_loans");

            while (resultSet.next()) {
                CarLoan loan = new CarLoan();
                loan.setId(resultSet.getLong("loan_id"));
                loan.setAge(resultSet.getInt("customer_age"));
                loan.setCar(resultSet.getString("car_name"));
                loan.setName(resultSet.getString("customer_name"));
                loan.setStartDate(resultSet.getDate("start_date"));
                loan.setReturnDate(resultSet.getDate("return_date"));
                loan.setEstimated_price(resultSet.getDouble("estimated_price"));

                carLoans.add(loan);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch all Car Loans", e);
        }
        return carLoans;
    }

    @Override
    public CarLoan findById(int id) {
        return null;
    }

    @Override
    public void save(CarLoan carLoan) {
        String query = "INSERT INTO car_loans (customer_age, car_name, customer_name, start_date, return_date, estimated_price) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.setInt(1, carLoan.getAge());
            preparedStatement.setString(2, carLoan.getCar());
            preparedStatement.setString(3, carLoan.getName());
            preparedStatement.setDate(4, new java.sql.Date(carLoan.getStartDate().getTime()));
            preparedStatement.setDate(5, new java.sql.Date(carLoan.getReturnDate().getTime()));
            preparedStatement.setDouble(6, carLoan.getEstimated_price());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating loan failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save Car Loan.", e);
        }
    }

    @Override
    public boolean checkAvailability(String car, java.sql.Date startDate, java.sql.Date returnDate) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT COUNT(*) FROM car_loans WHERE car_name = ? AND ((start_date <= ? AND return_date >= ?) OR (start_date <= ? AND return_date >= ?) OR (start_date >= ? AND return_date <= ?))";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, car);
                preparedStatement.setDate(2, startDate);
                preparedStatement.setDate(3, startDate);
                preparedStatement.setDate(4, returnDate);
                preparedStatement.setDate(5, returnDate);
                preparedStatement.setDate(6, startDate);
                preparedStatement.setDate(7, returnDate);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check availability of car.", e);
        }
        return false;
    }

    @Override
    public double getRevenue() throws SQLException {
        double revenue = 0;
        String query = "SELECT SUM(estimated_price) as total_revenue FROM car_loans";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                revenue = resultSet.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenue;
    }


}
