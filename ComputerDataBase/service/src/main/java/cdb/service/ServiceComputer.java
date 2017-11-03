package cdb.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdb.model.Computer;
import cdb.persistence.DAOComputer;

/**
 * Service providing an interface between the servlet and the Computer DAO.
 * @author aserre
 */
@Service
@Transactional
public class ServiceComputer {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceComputer.class);
    private DAOComputer dao;

    /**
     * Constructor.
     * @param dao {@link DAOComputer} bean
     */
    @Autowired
    public ServiceComputer(DAOComputer dao) {
        this.dao = dao;
    }

    /**
     * Creates a page of computers from the result of the search request.
     * @param search The search criteria
     * @param request The request format containing the pagination details
     * @return a list of computers in {@link Computer} format
     */
    @Transactional(readOnly = true)
    public Page<Computer> getPage(String search, PageRequest request) {
        try {
            return dao.findAllComputersByNameContainingOrCompanyNameContaining(request, search, search);
        } catch (RuntimeException e) {
            logger.error("Computer Service - Error during getPage : ", e);
            throw e;
        }
    }
    
    /**
     * Find all the {@link Computer} objects in the database
     * @return the list of all the computers
     */
    @Transactional(readOnly = true)
    public List<Computer> getAllComputers() {
        try {
            return dao.findAll();
        } catch (RuntimeException e) {
            logger.error("Computer Service - Error during getAllComputers : ", e);
            throw e;
        }
    }

    /**
     * Gets a specific computer from the database.
     * @param computerID ID of the computer we want to access
     * @return a computers in {@link Computer} format
     */
    @Transactional(readOnly = true)
    public Computer getComputer(String computerID) {
        try {
            return dao.getOne(Integer.parseInt(computerID));
        } catch (NumberFormatException | EntityNotFoundException e) {
            logger.error("Computer \"" + computerID + "\" not found : ", e);
            throw e;
        } catch (RuntimeException e) {
            logger.error("Computer Service - Error during getComputer : ", e);
            throw e;
        }
    }
    
    /**
     * Delete the requested computers from the database.
     * @param requestedDelete array of ID of the computers to delete
     */
    public void delete(int[] requestedDelete) {
        try {
            dao.deleteInBatchFromId(requestedDelete);
        } catch (RuntimeException e) {
            logger.error("Computer Service - Error during computer deletion : ", e);
            throw e;
        }
    }

    /**
     * Add a new computer to the database.
     * @param computer {@link Computer} to validate and add
     */
    public void saveComputer(Computer computer) {
            try {
                dao.save(computer);
            } catch (RuntimeException e) {
                logger.error("Computer Service - Error during addComputer : ", e);
                throw e;
            }
    }
}
