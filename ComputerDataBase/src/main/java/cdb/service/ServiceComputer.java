package cdb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cdb.dao.DAOComputer;
import cdb.dao.exceptions.DAOException;
import cdb.model.Computer;
import cdb.service.validation.ComputerValidator;
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

    @Autowired
    public void setDao(DAOComputer dao) {
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
    public Page<DTOComputer> getPage(String search, int pageNb, int limit, String col, String order) {
        Page<DTOComputer> ret = null;
        try {
            AtomicInteger count = new AtomicInteger();
            List<Computer> l = dao.getFromName((pageNb - 1) * limit, limit, count, search, col, order);
            ret = new Page<DTOComputer>(MapperComputer.toDTOComputer(l), pageNb, limit, count.get(), col, order);
            ret.setSearch(search);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
        return ret;
    }

    /**
     * Delete the requested computers from the database.
     * @param requestedDelete ID of the computers to delete, spearated by a comma
     */
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
    public DTOComputer getComputer(String computerID) {
        DTOComputer c = new DTOComputer();
        try {
            Computer comp = dao.getFromId(Integer.parseInt(computerID)).get(0);
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
     * @param name Name of the new computer
     * @param introduced Introduced date of the new computer, in 'dd-MM-yyyy' format
     * @param discontinued Discontinued date of the new computer, in 'dd-MM-yyyy' format
     * @param companyId companyId of the new computer
     * @return a list of errors that occured during validation
     */
    public Map<String, String> addComputer(String name, String introduced, String discontinued, String companyId) {
        Map<String, String> errors = new HashMap<String, String>();
        Computer c = ComputerValidator.validate(name, introduced, discontinued, companyId, errors);
        if (errors.isEmpty()) {
            try {
                dao.create(c);
            } catch (DAOException e) {
                logger.error(e.getMessage());
            }
        }
        return errors;
    }

    /**
     * Edit an existing computer.
     * @param id ID of the computer
     * @param name Name of the computer
     * @param introduced Introduced date of the computer, in 'dd-MM-yyyy' format
     * @param discontinued Discontinued date of the computer, in 'dd-MM-yyyy' format
     * @param companyId companyId of the computer
     * @return a list of errors that occured during validation
     */
    public Map<String, String> editComputer(String id, String name, String introduced, String discontinued,
            String companyId) {
        Map<String, String> errors = new HashMap<String, String>();
        Computer c = ComputerValidator.validate(id, name, introduced, discontinued, companyId, errors);
        if (errors.isEmpty()) {
            try {
                dao.update(c);
            } catch (DAOException e) {
                logger.error(e.getMessage());
            }
        }
        return errors;
    }
}
