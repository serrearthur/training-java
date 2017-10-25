package cdb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cdb.model.Computer;

//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import cdb.dao.exceptions.DAOException;
//import cdb.dao.impl.DAOComputerImpl;
//import cdb.model.Computer;

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
    @Query("DELETE FROM Computer c WHERE c.id IN :idList")
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
    @Query(value = "SELECT * FROM Computer cpt LEFT JOIN Company cpn ON cpt.company_id = cpn.id WHERE cpt.name LIKE :name OR cpn.name LIKE :name ORDER BY :col :order LIMIT :start , :limit", nativeQuery = true)
    List<Computer> findByNameAndCompanyName(@Param("name") String name, @Param("col") String col, @Param("order") String order, @Param("start") Integer start, @Param("limit") Integer limit);

    /**
    * Get the number of computers that have a specified name.
    * @param name name of the computers
    * @return the count of {@link Computer} from the request
    */
    @Query("SELECT COUNT(*) FROM Computer cpt LEFT JOIN Company cpn ON cpt.companyId = cpn.id WHERE cpt.name LIKE :name OR cpn.name LIKE :name ")
    int countByNameAndCompanyName(@Param("name") String name);
//    /**
//     * Method to create a computer in the database.
//     * @param computer {@link Computer} to be created
//     * @throws DAOException thrown when a connection problem happens.
//     */
//    void create(Computer computer) throws DAOException;
//
//    /**
//     * Method to update a computer in the database.
//     * @param computer {@link Computer} to be updated
//     * @throws DAOException thrown when a connection problem happens.
//     */
//    void update(Computer computer) throws DAOException;
//
//    /**
//     * Method to delete a computer in the database.
//     * @param idList list of id of the {@link Computer} to be deleted
//     * @return the number of rows deleted
//     * @throws DAOException thrown when a connection problem happens.
//     */
//    int delete(List<String> idList) throws DAOException;
//
//    /**
//     * Method to delete all computers with a specific companyId.
//     * @param companyId companyId of the computers to be deleted
//     * @return the number of rows deleted
//     * @throws DAOException thrown when a connection problem happens.
//     */
//    int deleteCompanyId(Integer companyId) throws DAOException;
//
//    /**
//     * Get a computer that have a specified id.
//     * @param id ID of the computers
//     * @return a list of the corresponding computers
//     * @throws DAOException thrown when a connection problem happens.
//     */
//    Computer getFromId(Integer id) throws DAOException;
//
//    /**
//     * Get a list of computers that have a specified copanyId.
//     * @param companyId CompanyId of the computers
//     * @return a list of the corresponding computers
//     */
//    List<Computer> getFromCompanyId(Integer companyId);
//
//    /**
//     * Get a list of computers that have a specified name.
//     * @param start starting index
//     * @param limit limit for the page size
//     * @param count variable used to store the row count
//     * @param name name of the computers
//     * @param col column to order by
//     * @param order "ASC" or "DESC"
//     * @return a list of the corresponding computers
//     * @throws DAOException thrown when a connection problem happens.
//     */
//    List<Computer> getFromName(Integer start, Integer limit, AtomicInteger count, String name, String col, String order) throws DAOException;
}
