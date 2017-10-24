package cdb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdb.dao.DAOComputer;
import cdb.dao.exceptions.DAOException;
import cdb.model.Computer;
import cdb.view.Page;
import cdb.view.dto.DTOComputer;
import cdb.view.mapper.MapperComputer;

/**
 * Service providing an interface between the servlet and the Computer DAO.
 * @author aserre
 */
@Service
public class ServiceComputer {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceComputer.class);
    private DAOComputer dao;

    /**
     * Constructor.
     * @param dao {@link DAOComputer} bean
     */
    @Autowired
    private ServiceComputer(DAOComputer dao) {
        this.dao = dao;
    }

    /**
     * Creates a page of computers from the result of the search request.
     * @param search The search criteria
     * @param pageNb the page number to get
     * @param limit The maximum number of Computer per page
     * @param col column to order by
     * @param order "ASC" or "DESC"
     * @return a list of computers in {@link DTOComputer} format
     */
    @Transactional
    public Page<DTOComputer> getPage(String search, int pageNb, int limit, String col, String order) {
        Page<DTOComputer> ret = null;
        try {
            AtomicInteger count = new AtomicInteger();
            List<Computer> l = dao.getFromName((pageNb - 1) * limit, limit, count, search, col, order);
            ret = new Page<DTOComputer>(MapperComputer.toDTOComputer(l), pageNb, limit, count.get(), col, order, search);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
        return ret;
    }

    /**
     * Delete the requested computers from the database.
     * @param requestedDelete ID of the computers to delete, spearated by a comma
     */
    @Transactional
    public void delete(String requestedDelete) {
        try {
            dao.delete(requestedDelete);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Gets a specific computer from the database.
     * @param computerID ID of the computer we want to access
     * @return a computers in {@link DTOComputer} format
     */
    @Transactional
    public DTOComputer getComputer(String computerID) {
        DTOComputer c = new DTOComputer();
        try {
            Computer comp = dao.getFromId(Integer.parseInt(computerID));
            c = MapperComputer.toDTOComputer(comp);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            logger.error(e.getMessage() + " : Computer \"" + computerID + "\" not found : ");
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
        return c;
    }

    /**
     * Add a new computer to the database.
     * @param computer {@link DTOComputer} to validate and add
     * @return a list of errors that occured during validation
     */
    @Transactional
    public Map<String, String> addComputer(DTOComputer computer) {
        Map<String, String> errors = new HashMap<String, String>();
//        Computer valid = ComputerValidator.validateAdd(computer, errors);
        Computer valid = MapperComputer.toComputer(computer);
        if (errors.isEmpty()) {
            try {
                dao.create(valid);
            } catch (DAOException e) {
                logger.error(e.getMessage());
            }
        }
        return errors;
    }

    /**
     * Edit an existing computer.
     * @param computer {@link DTOComputer} to validate and edit
     * @return a list of errors that occured during validation
     */
    @Transactional
    public Map<String, String> editComputer(DTOComputer computer) {
        Map<String, String> errors = new HashMap<String, String>();
//        Computer valid = ComputerValidator.validateEdit(computer, errors);
        Computer valid = MapperComputer.toComputer(computer);
        if (errors.isEmpty()) {
            try {
                dao.update(valid);
            } catch (DAOException e) {
                logger.error(e.getMessage());
            }
        }
        return errors;
    }
}
