/**
 * 
 */
package net.najiboulhouch.springdataoverview.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit4.SpringRunner;

import net.najiboulhouch.springdataoverview.entity.Flight;

/**
 * @author NAJIB
 * @version 1.0 
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class PagingAndSortingTests {

	@Autowired
	private FlightSortedRepository flightSortedRepository;
	
	/**
	 * Delete all elements in Flight table
	 */
	@Before
	public void setUp() {
		flightSortedRepository.deleteAll();
	}
	
	/**
	 * Test sorted flights by destination
	 */
	@Test
	public void shouldSortFlightsByDestination() {
		final Flight madrid = CreateFlight("Madrid");
		final Flight london = CreateFlight("London");
		final Flight paris  = CreateFlight("Paris");
		
		flightSortedRepository.save(madrid);
		flightSortedRepository.save(london);
		flightSortedRepository.save(paris);
		
		final Iterable<Flight> flights = flightSortedRepository.findAll(Sort.by("destination"));
		
		assertThat(flights).hasSize(3);
		
		final Iterator<Flight> iterator = flights.iterator();
		
		assertThat(iterator.next().getDestination()).isEqualTo("London");
		assertThat(iterator.next().getDestination()).isEqualTo("Madrid");
		assertThat(iterator.next().getDestination()).isEqualTo("Paris");		
	}
	
	/**
	 * Test sorted flights by schedultime and name
	 */
	@Test
	public void shouldSortFlightsByScheduledAndThenName() {
		 final LocalDateTime now = LocalDateTime.now();
		 final Flight flightOne = CreateFlight("Paris" , now);
		 final Flight flightTwo = CreateFlight("Paris" , now.plusHours(2));
		 final Flight flightThree = CreateFlight("Paris" , now.minusHours(1));		  
	
		 final Flight flightLondonOne = CreateFlight("London" , now.plusHours(1));
		 final Flight flightLondonTwo = CreateFlight("London" , now);
		
		 flightSortedRepository.save(flightOne);
		 flightSortedRepository.save(flightThree);
		 flightSortedRepository.save(flightTwo);
		 flightSortedRepository.save(flightLondonOne);
		 flightSortedRepository.save(flightLondonTwo);
		 
		 final Iterable<Flight> flights = flightSortedRepository.findAll(Sort.by("destination" , "scheduledAt"));
		 assertThat(flights).hasSize(5);
		 
		 final Iterator<Flight> iterator = flights.iterator();
		 assertThat(iterator.next()).isEqualToComparingFieldByField(flightLondonTwo);
		 assertThat(iterator.next()).isEqualToComparingFieldByField(flightLondonOne);
		 assertThat(iterator.next()).isEqualToComparingFieldByField(flightThree);
		 assertThat(iterator.next()).isEqualToComparingFieldByField(flightOne);
		 assertThat(iterator.next()).isEqualToComparingFieldByField(flightTwo);
	}
	
	/**
	 * Test find flights by page result
	 */
	@Test
	public void shouldPageResults() {
		for (int i = 0; i < 50; i++) {
			flightSortedRepository.save(CreateFlight(String.valueOf(i)));
		}
		
		final Page<Flight> page =  flightSortedRepository.findAll(PageRequest.of(2, 5));
		
		assertThat(page.getTotalElements()).isEqualTo(50);
		assertThat(page.getNumberOfElements()).isEqualTo(5);
		assertThat(page.getTotalPages()).isEqualTo(10);
		assertThat(page.getContent())
			.extracting(Flight::getDestination)
			.containsExactly("10", "11", "12" , "13", "14");
	}
	
	/**
	 * Test find sorted and paged flights 
	 */
	@Test
	public void shouldPageAndSortResults() {
		for (int i = 0; i < 50; i++) {
			flightSortedRepository.save(CreateFlight(String.valueOf(i)));
		}
		
		final Page<Flight> page =  flightSortedRepository
				.findAll(PageRequest.of(2, 5 , Sort.by(Direction.DESC , "destination")));
		
		assertThat(page.getTotalElements()).isEqualTo(50);
		assertThat(page.getNumberOfElements()).isEqualTo(5);
		assertThat(page.getTotalPages()).isEqualTo(10);
		assertThat(page.getContent())
			.extracting(Flight::getDestination)
			.containsExactly("44", "43", "42" , "41", "40");
	}
	
	/**
	 * Test find sorted and paged flights for DirevedQueries 
	 */
	@Test
	public void shouldPageAndSortDirevedQuery() {
		for (int i = 0; i < 10; i++) {
			final Flight flight = CreateFlight(String.valueOf(i));
			flight.setOrigin("Paris");
			flightSortedRepository.save(flight);
		}
		
		for (int i = 0; i < 10; i++) {
			final Flight flight = CreateFlight(String.valueOf(i));
			flight.setOrigin("London");
			flightSortedRepository.save(flight);
		}
		
		final Page<Flight> page =  flightSortedRepository
				.findByOrigin("London" , PageRequest.of(0, 5 , Sort.by(Direction.DESC , "destination")));
		
		assertThat(page.getTotalElements()).isEqualTo(10);
		assertThat(page.getNumberOfElements()).isEqualTo(5);
		assertThat(page.getTotalPages()).isEqualTo(2);
		assertThat(page.getContent())
			.extracting(Flight::getDestination)
			.containsExactly("9", "8", "7" , "6", "5");
	}
	
	/** 
	 * @param destination
	 * @param scheduledAt
	 * @return new Flight with destination and time parameters
	 */
	private Flight CreateFlight(String destination,  LocalDateTime scheduledAt) {
		Flight flight = new Flight();
		flight.setOrigin("London");
		flight.setDestination(destination);
		flight.setScheduledAt(scheduledAt);
		return flight;
	}
	
	/**
	 * @param destination
	 * @return created Flight object with destination parameter
	 */
	private Flight CreateFlight(String destination) {
		return CreateFlight(destination , LocalDateTime.parse("2011-01-01T12:02:01"));
	}

}
