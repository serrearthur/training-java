package cdb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdb.model.Computer;
import cdb.persistence.DAOComputer;
import cdb.persistence.exceptions.DAOException;
import cdb.view.dto.DTOComputer;
import cdb.view.mapper.MapperComputer;

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
     * @param pageNb the page number to get
     * @param limit The maximum number of Computer per page
     * @param col column to order by
     * @param order "ASC" or "DESC"
     * @return a list of computers in {@link DTOComputer} format
     */
    public Page<DTOComputer> getPage(String search, int pageNb, int limit, String col, String order) {
        Page<DTOComputer> ret = null;
        PageRequest request = PageRequest.of(pageNb, limit, order.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, col);
        try {
            Page<Computer> page = dao.findAllComputersByNameContainingOrCompanyNameContaining(request, search, search);
            ret = new PageImpl<DTOComputer>(MapperComputer.toDTOComputer(page.getContent()), request, getCount(search));
        } catch (DAOException | HibernateException e) {
            logger.error("Computer Service - Error during getPage : ", e);
        }
        return ret;
    }
    
    public int getCount(String search) {
        return dao.countByNameContainingOrCompanyNameContaining(search, search);
    }
    
    public List<DTOComputer> getAllComputers() {
        List<DTOComputer> ret = null;
        try {
            List<Computer> l = dao.findAll();
            ret = MapperComputer.toDTOComputer(l);
        } catch (DAOException e) {
            logger.error("Computer Service - Error during getAllComputers : ", e);
        }
        return ret;
    }

    /**
     * Delete the requested computers from the database.
     * @param requestedDelete ID of the computers to delete, spearated by a comma
     */
    public void delete(int[] requestedDelete) {
        try {
            dao.deleteInBatchFromId(requestedDelete);
        } catch (DAOException | HibernateException e) {
            logger.error("Computer Service - Error during computer deletion : ", e);
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
            Computer comp = dao.getOne(Integer.parseInt(computerID));
            c = MapperComputer.toDTOComputer(comp);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            logger.error("Computer \"" + computerID + "\" not found : ", e);
        } catch (DAOException | HibernateException e) {
            logger.error("Computer Service - Error during getComputer : ", e);
        }
        return c;
    }

    /**
     * Add a new computer to the database.
     * @param computer {@link DTOComputer} to validate and add
     * @return a list of errors that occured during validation
     */
    public Map<String, String> addComputer(DTOComputer computer) {
        Map<String, String> errors = new HashMap<String, String>();
//        Computer valid = ComputerValidator.validateAdd(computer, errors);
        Computer valid = MapperComputer.toComputer(computer);
        if (errors.isEmpty()) {
            try {
                dao.save(valid);
            } catch (DAOException | HibernateException e) {
                logger.error("Computer Service - Error during addComputer : ", e);
            }
        }
        return errors;
    }

    /**
     * Edit an existing computer.
     * @param computer {@link DTOComputer} to validate and edit
     * @return a list of errors that occured during validation
     */
    public Map<String, String> editComputer(DTOComputer computer) {
        Map<String, String> errors = new HashMap<String, String>();
//        Computer valid = ComputerValidator.validateEdit(computer, errors);
        Computer valid = MapperComputer.toComputer(computer);
        if (errors.isEmpty()) {
            try {
                dao.save(valid);
            } catch (DAOException | HibernateException e) {
                logger.error("Computer Service - Error during editComputer : ", e);
            }
        }
        return errors;
    }
}
