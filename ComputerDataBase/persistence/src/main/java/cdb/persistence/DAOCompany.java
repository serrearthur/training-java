package cdb.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import cdb.model.Company;

/**
 * Interface representing the methods used by a {@link DAOCompanyImpl}.
 * @author aserre
 */
public interface DAOCompany extends JpaRepository<Company, Integer> {

}
