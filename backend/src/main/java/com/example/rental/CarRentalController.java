package com.example.rental;

import com.example.rental.repository.CarRentalRepository;
import com.example.rental.service.CarRentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class CarRentalController {

	private final CarRentalRepository carRentalRepository;
	private final CarRentalService carRentalService;

	public CarRentalController(CarRentalRepository carRentalRepository, CarRentalService carRentalService) {
		this.carRentalRepository = carRentalRepository;
		this.carRentalService = carRentalService;
	}

	/**
	 * Retrieves all car loans from database.
	 * @return List of car loans
	 */

	@GetMapping ("/getAll")
	public List<CarLoan> getLoans(){
		return carRentalService.getLoans();
	}

	/**
	 * Method checks if a certain car is occupied during certain dates.
	 * MVP is build with context that car dealership has 1 of each car.
	 * @param car
	 * @param startDate
	 * @param returnDate
	 * @return HttpStatus 200 or HttpStatus 400
	 */

	@GetMapping("/checkAvailability")
	public ResponseEntity<Boolean> checkAvailability(@RequestParam String car, @RequestParam String startDate, @RequestParam String returnDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sqlStartDate = null;
		java.sql.Date sqlEndDate = null;

		try {
			java.util.Date parsedStartDate = format.parse(startDate);
			java.util.Date parsedEndDate = format.parse(returnDate);
			sqlStartDate = new java.sql.Date(parsedStartDate.getTime());
			sqlEndDate = new java.sql.Date(parsedEndDate.getTime());
		} catch (ParseException e) {
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		}
		boolean isAvailable = carRentalService.checkCarAvailability(car, sqlStartDate, sqlEndDate);
		return new ResponseEntity<>(isAvailable, HttpStatus.OK);
	}

	/**
	 * Method receives a data object from the frontend and saves it as a car_loan in the database.
	 * @param carLoan
	 * @return HttpStatus 201 or HttpStatus 500
	 */
	@PostMapping("/add")
	public ResponseEntity<?> addCarLoan(@RequestBody CarLoan carLoan) {
		try {
			carRentalService.save(carLoan);
			return new ResponseEntity<>("Loan has been added successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Unable to add loan: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Method retrieves SUM of car_loans estimated_price from database.
	 * @return double revenue
	 * @throws SQLException
	 */
	@GetMapping ("/getRevenue")
	public double getRevenue() throws SQLException {
		return carRentalService.getRevenue();
	}




}
