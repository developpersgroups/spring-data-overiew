/**
 * 
 */
package net.najiboulhouch.springdataoverview.repository;

import javax.persistence.EntityManager;

/**
 * @author NAJIB
 * @version 1.0 
 */
public class DeleteByOriginRepositoryImpl implements DeleteByOriginRepository{

	private final EntityManager entityManager;
	
	/**
	 * @param entityManager
	 */
	public DeleteByOriginRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Delete flight line by origin value
	 */
	@Override
	public void deleteByOrigin(String origin) {
		entityManager.createNativeQuery("delete from Flight where origin = ? ")
		.setParameter(1, origin)
		.executeUpdate();
		
	}

}
