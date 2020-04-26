/**
 * 
 */
package net.najiboulhouch.springdataoverview.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.najiboulhouch.springdataoverview.entity.Flight;

/**
 * @author NAJIB
 * @version 1.0 
 */
@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> , DeleteByOriginRepository {

	/**
	 * @param origin
	 * @return list of Flights by origin
	 */
	List<Flight> findByOrigin(String origin);

	/**
	 * @param origin
	 * @param destination
	 * @return list of flights by origin and destination
	 */
	List<Flight> findByOriginAndDestination(String origin, String destination);


	/**
	 * 
	 * @param origins
	 * @return list of flights by origins
	 */
	List<Flight> findByOriginIn(String ... origins);

	/**
	 * @param origin
	 * @return list of flights by origin ignoring case
	 */
	List<Flight> findByOriginIgnoreCase(String origin);
}
