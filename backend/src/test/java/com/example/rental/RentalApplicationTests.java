package com.example.rental;
import com.example.rental.repository.CarRentalRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
		locations = "classpath:application-integrationtest.properties")
@TestMethodOrder(OrderAnnotation.class)
class RentalApplicationTests {


	/**
	 * FYI: These tests are passed if the database is set-up but empty since some things are hardcoded, like how many loans.
	 * I have used the TestMethodOrder to make sure that the hard-coded tests are run at the correct time.
	 */

	@Autowired
	private MockMvc mvc;
	@Autowired
	CarRentalRepository carRentalRepository;

	@Test
	@Order(1)
	void shouldCreateNewLoan() throws Exception {
		String loanJson = "{\"age\":\"25\", \"name\":\"John Doe\", \"car\":\"Volvo V60\", \"startDate\":\"2023-09-20\", \"returnDate\":\"2023-09-25\", \"estimated_price\":1000}";

		this.mvc.perform(MockMvcRequestBuilders.post("/api/add")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loanJson))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string("Loan has been added successfully"));
	}


	@Test
	@Order(2)
	void shouldReturnAllLoans() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders
						.get("/api/getAll")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()", Matchers.is(1)))
				.andExpect(jsonPath("$[0].name", Matchers.is("John Doe")));

	}



	@Test
	@Order(3)
	void shouldReturnRevenue() throws Exception {

		this.mvc.perform(get("/api/getRevenue"))
				.andExpect(status().isOk())
				.andExpect(content().string("1000.0"));
	}

	/**
	 * CarRentalRepositoryImpl tests
	 */

	@Test
	@Order(4)
	public void testSave() {
		CarLoan carLoan = new CarLoan();
		carLoan.setAge(45);
		carLoan.setCar("Ford Mustang");
		carLoan.setName("Ben Test");
		carLoan.setStartDate(Date.valueOf("2023-10-10"));
		carLoan.setReturnDate(Date.valueOf("2023-10-15"));
		carLoan.setEstimated_price(1000.0);
		carRentalRepository.save(carLoan);

		List<CarLoan> loans = carRentalRepository.findAll();
		assertTrue(loans.size() > 0, "Loans list should not be empty");

		boolean hasJohnTest = loans.stream().anyMatch(loan -> "Ben Test".equals(loan.getName()));
		assertTrue(hasJohnTest, "Loans list should contain a loan with the name 'Ben Test'");
	}

	@Test
	@Order(5)
	public void testFindAll() {
			List<CarLoan> loans = carRentalRepository.findAll();
			assertEquals(2, loans.size(), "Loans list should contain two loans");
		}


	@Test
	@Order(6)
	public void testCheckAvailability() {
		boolean available = carRentalRepository.checkAvailability("Ford Mustang", Date.valueOf("2023-10-09"), Date.valueOf("2023-10-20"));
		boolean available2 = carRentalRepository.checkAvailability("Ford Mustang", Date.valueOf("2023-12-09"), Date.valueOf("2023-12-20"));
		assertFalse(available);
		assertTrue(available2);
	}

	@Test
	@Order(7)
	public void testGetRevenue() throws SQLException {
		double revenue = carRentalRepository.getRevenue();
		assertEquals(revenue, 2000);
	}

}
