package net.najiboulhouch.springdataoverview.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import net.najiboulhouch.springdataoverview.entity.Flight;

/**
 * 
 * @author NAJIB
 * @version 1.0
 */

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CrudTests {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private FlightRepository flightREpository;

	/**
	 * Check if Flight can be saved in H2 embeded table
	 */
	@Test
	public void verifyFlightCanBeSaved() {
		final Flight flight = new Flight();
		flight.setOrigin("Rabat");
		flight.setDestination("Madrid");
		flight.setScheduledAt(LocalDateTime.parse("2010-01-01T13:12:00"));

		entityManager.persist(flight);

		final TypedQuery<Flight> resultsQuery = entityManager.createQuery("SELECT f FROM Flight f", Flight.class);

		final List<Flight> flights = resultsQuery.getResultList();

		assertThat(flights).hasSize(1).first().isEqualTo(flight);
	}

	/**
	 * Test CRUD operations
	 */
	@Test
	public void shouldPerformCRUDOperations() {
		final Flight flight = new Flight();
		flight.setOrigin("London");
		flight.setDestination("New York");
		flight.setScheduledAt(LocalDateTime.parse("2014-01-01T13:12:00"));

		flightREpository.save(flight);

		assertThat(flightREpository.findAll()).hasSize(1).first().isEqualToComparingFieldByField(flight);

		flightREpository.deleteById(flight.getId());

		assertThat(flightREpository.count()).isZero();
	}

}
