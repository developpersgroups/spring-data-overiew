/**
 * 
 */
package net.najiboulhouch.springdataoverview.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.najiboulhouch.springdataoverview.entity.Flight;

/**
 * @author NAJIB
 * @version 1.0 
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class DerivedQueryTests {

	@Autowired
	private FlightRepository flightRepository;
	
	/**
	 * Delete all elements in Flight table
	 */
	@Before
	public void setUp() {
		flightRepository.deleteAll();
	}
	
	/**
	 * Find Flights from London
	 */
	@Test
	public void shouldFindFlightsFromLondon() {
		final Flight flightOne = CreateFlight("London");
		final Flight flightTwo = CreateFlight("London");
		final Flight flightThree = CreateFlight("New York");
		
		flightRepository.save(flightOne);
		flightRepository.save(flightTwo);
		flightRepository.save(flightThree);
		
		List<Flight> flightsToLondon = flightRepository.findByOrigin("London");
		
		assertThat(flightsToLondon).hasSize(2);
		assertThat(flightsToLondon.get(0)).isEqualToComparingFieldByField(flightOne);
		assertThat(flightsToLondon.get(1)).isEqualToComparingFieldByField(flightTwo);
	}
	
	/**
	 * Test find flights from London to Paris
	 */
	@Test
	public void shouldFindFlightsFromLondonToParis() {
		final Flight flightOne = CreateFlight("London" , "Paris");
		final Flight flightTwo = CreateFlight("London" , "New York");
		final Flight flightThree = CreateFlight("Madrid" , "Paris");
		
		flightRepository.save(flightOne);
		flightRepository.save(flightTwo);
		flightRepository.save(flightThree);		
		
		List<Flight> londonToParis = flightRepository.findByOriginAndDestination("London" , "Paris");
		
		assertThat(londonToParis).hasSize(1).first().isEqualToComparingFieldByField(flightOne);
	}
	
	/**
	 * Test find flights from London or Paris
	 */
	@Test
	public void shouldFindFlightsFromLondonOrParis() {
		final Flight flightOne = CreateFlight("London" , "Paris");
		final Flight flightTwo = CreateFlight("Tokyo" , "New York");
		final Flight flightThree = CreateFlight("Madrid" , "New York");
		
		flightRepository.save(flightOne);
		flightRepository.save(flightTwo);
		flightRepository.save(flightThree);
		
		List<Flight> londonOrParis = flightRepository.findByOriginIn("London" , "Madrid");
		
		assertThat(londonOrParis).hasSize(2);
		assertThat(londonOrParis.get(0)).isEqualToComparingFieldByField(flightOne);
		assertThat(londonOrParis.get(1)).isEqualToComparingFieldByField(flightThree);		
		
	}
	
	/**
	 * Test find flights from London with ighnoring case
	 */
	@Test
	public void shouldFindFlightsFromLondonIgnoringCase() {
		Flight flight = CreateFlight("LONDON");
		
		flightRepository.save(flight);
		
		final List<Flight> flightsToLondon = flightRepository.findByOriginIgnoreCase("London");
		
		assertThat(flightsToLondon).hasSize(1).first().isEqualToComparingFieldByField(flight);
	}

	/** 
	 * @param origin
	 * @param destination
	 * @return new Flight with origin and destination parameters
	 */
	private Flight CreateFlight(String origin, String destination) {
		Flight flight = new Flight();
		flight.setOrigin(origin);
		flight.setDestination(destination);
		flight.setScheduledAt(LocalDateTime.now());
		return flight;
	}

	/**
	 * @param origin
	 * @return created Flight object with origin parameter
	 */
	private Flight CreateFlight(String origin) {
		Flight flight = new Flight();
		flight.setOrigin(origin);
		flight.setDestination("Madrid");
		flight.setScheduledAt(LocalDateTime.now()); 
		return flight;
	}
	
}
