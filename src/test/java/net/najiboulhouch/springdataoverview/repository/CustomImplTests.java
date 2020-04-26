/**
 * 
 */
package net.najiboulhouch.springdataoverview.repository;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
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
public class CustomImplTests {

	@Autowired
	private FlightRepository flightRepository;
	
	/**
	 * Test custom implementaton of repositoy
	 */
	@Test
	public void shouldSaveCustomImpl() {
		final Flight toDelete = createFlight("London");		
		final Flight toKeep = createFlight("Paris");
		flightRepository.save(toDelete);
		flightRepository.save(toKeep);
		
		flightRepository.deleteByOrigin("London");
		
		Assertions.assertThat(flightRepository.findAll())
		.hasSize(1)
		.first()
		.isEqualToComparingFieldByField(toKeep);
	}

	/**
	 * @param origin
	 * @return created Flight object
	 */
	private Flight createFlight(String origin) {
		Flight flight = new Flight();
		flight.setOrigin(origin);
		flight.setDestination("Tokyo");
		flight.setScheduledAt(LocalDateTime.now()); 
		return flight;
	}
}
