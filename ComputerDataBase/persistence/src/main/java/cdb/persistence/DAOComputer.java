package cdb.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cdb.model.Computer;

/**
 * Interface representing the method used by a {@link DAOComputerImpl}.
 * @author aserre
 */
public interface DAOComputer extends JpaRepository<Computer, Integer> {
    /**
    * Method to delete all computers with a specific companyId.
    * @param companyId companyId of the computers to be deleted
    */
    @Transactional
    void deleteByCompanyId(Integer companyId);

    /**
    * Method to delete a computer in the database.
    * @param idList array of id of the {@link Computer} to be deleted
    */
    @Transactional
    @Modifying
    @Query("DELETE FROM Computer c WHERE c.id IN (:idList)")
    void deleteInBatchFromId(@Param("idList") int[] idList);

    int countByNameContainingOrCompanyNameContaining(String name, String companyName);

    Page<Computer> findAllComputersByNameContainingOrCompanyNameContaining(Pageable page, String name, String companyName);
}
