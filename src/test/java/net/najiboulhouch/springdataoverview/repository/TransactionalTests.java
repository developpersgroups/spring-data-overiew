/**
 * 
 */
package net.najiboulhouch.springdataoverview.repository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.najiboulhouch.springdataoverview.entity.Flight;
import net.najiboulhouch.springdataoverview.service.FlightService;

/**
 * @author NAJIB
 * @version 1.0 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalTests {

	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private FlightService flightService;
	
	/**
	 * Delete all elements in Flight table
	 */
	@Before
	public void setUp() {
		flightRepository.deleteAll();
	}
	
	/**
	 * Test saved Flight with no Transactional mode
	 */
	@Test
	public void shouldNotRollBackWhenTheresNoTransactional() {
		try {
			flightService.saveFlight(new Flight());
		} catch (Exception e) {
			
		}
		finally {
			Assertions.assertThat(flightRepository.findAll())
			.isEmpty();
		}
	}
	
	/**
	 * Test saved Flight with Transactional mode
	 */
	@Test
	public void shouldNotRollBackWhenTheresIsATransaction() {
		try {
			flightService.saveFlightTransactional(new Flight());
		} catch (Exception e) {
			
		}
		finally {
			Assertions.assertThat(flightRepository.findAll())
			.isEmpty();
		}
	}
}
