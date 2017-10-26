package cdb.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    void deleteByCompanyId(Integer companyId);

    /**
    * Method to delete a computer in the database.
    * @param idList list of id of the {@link Computer} to be deleted
    */
    @Query("DELETE FROM Computer c WHERE c.id IN (:idList)")
    void deleteInBatchFromId(@Param("idList") List<String> idList);

    /**
    * Get a list of computers that have a specified name.
    * @param name name of the computers
    * @param col column to order by
    * @param order "ASC" or "DESC"
    * @param start starting index
    * @param limit limit for the page size
    * @return a list of the corresponding computers
    */
    @Query(value = "SELECT * FROM computer cpt LEFT JOIN company cpn ON cpt.company_id = cpn.id WHERE cpt.name LIKE :name OR cpn.name LIKE :name ORDER BY :col :order LIMIT :start , :limit", nativeQuery = true)
    List<Computer> findByNameAndCompanyName(@Param("name") String name, @Param("col") String col, @Param("order") String order, @Param("start") Integer start, @Param("limit") Integer limit);

    /**
    * Get the number of computers that have a specified name.
    * @param name name of the computers
    * @return the count of {@link Computer} from the request
    */
    @Query("SELECT COUNT(*) FROM Computer cpt LEFT JOIN Company cpn ON cpt.companyId = cpn.id WHERE cpt.name LIKE :name OR cpn.name LIKE :name ")
    int countByNameAndCompanyName(@Param("name") String name);
}
