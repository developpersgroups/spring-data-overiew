/**
 * 
 */
package net.najiboulhouch.springdataoverview.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.najiboulhouch.springdataoverview.entity.Flight;
import net.najiboulhouch.springdataoverview.repository.FlightRepository;

/**
 * @author NAJIB
 * @version 1.0 
 */

@Component
public class FlightService {

	private final FlightRepository flightRepository = null;

	public FlightRepository getFlightRepository() {
		return flightRepository;
	}
	
	/**
	 * @param flight
	 * Save flight in the table
	 */
	public void saveFlight(Flight flight) {
		flightRepository.save(flight);
		throw new RuntimeException("I failed");	
	}

	/**
	 * @param flight
	 * Save flight object in the table 
	 * Active Transactional mode 
	 */
	@Transactional
	public void saveFlightTransactional(Flight flight) {
		flightRepository.save(flight);
		throw new RuntimeException("I failed");			
	}
}
