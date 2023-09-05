package com.example.rental;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;




public class CarLoan {

    private Long id;
    private int age;
    private String car;
    private String name;
    private Date startDate;
    private Date returnDate;
    private double estimated_price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getEstimated_price() {
        return estimated_price;
    }

    public void setEstimated_price(double estimated_price) {
        this.estimated_price = estimated_price;
    }
}
