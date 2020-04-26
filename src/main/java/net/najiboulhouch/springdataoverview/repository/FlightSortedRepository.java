/**
 * 
 */
package net.najiboulhouch.springdataoverview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import net.najiboulhouch.springdataoverview.entity.Flight;

/**
 * @author NAJIB
 * @version 1.0 
 */

@Repository
public interface FlightSortedRepository extends PagingAndSortingRepository<Flight, Long> {

	/**
	 * @param origin
	 * @param page
	 * @return list of flights by origin and page paramters
	 */
	Page<Flight> findByOrigin(String origin	, Pageable page);

	
}
