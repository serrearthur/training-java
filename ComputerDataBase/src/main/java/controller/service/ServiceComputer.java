package controller.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import controller.validation.ComputerValidator;
import dao.exceptions.DAOException;
import dao.impl.DAOComputer;
import model.Computer;
import view.Page;
import view.dto.DTOComputer;
import view.mapper.MapperComputer;

/**
 * Service providing an interface between the servlet and the Computer DAO.
 * @author aserre
 */
public class ServiceComputer {
    private DAOComputer dao;
    /**
     * Initialization-on-demand singleton holder  for {@link ServiceComputer}.
     */
    private static class SingletonHolder {
        private static final ServiceComputer INSTANCE = new ServiceComputer();
    }

    /**
     * Accessor for the instance of the singleton.
     * @return the instance of {@link ServiceComputer}
     */
    public static ServiceComputer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Contructor for a new ServiceComputer.
     */
    private ServiceComputer() {
        this.dao = DAOComputer.getInstance();
    }

    /**
     * Creates a page of computers from the result of the search request.
     * @param search The search criteria
     * @param pageNb the page number to get
     * @param limit The maximum number of Computer per page
     * @param col column to order by
     * @return a list of computers in {@link DTOComputer} format
     */
    public Page<DTOComputer> getPage(String search, int pageNb, int limit, String col) {
        Page<DTOComputer> ret = null;
        try {
            AtomicInteger count = new AtomicInteger();
            List<Computer> l = dao.getFromName((pageNb - 1) * limit, limit, count, search, col);
            ret = new Page<DTOComputer>(MapperComputer.toDTOComputer(l), pageNb, limit, count.get(), col);
            ret.setSearch(search);
        } catch (DAOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            System.out.println("Computer \"" + computerID + "\" not found : " + e.getMessage());
        } catch (DAOException e) {
            e.printStackTrace();
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
                e.printStackTrace();
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
                e.printStackTrace();
            }
        }
        return errors;
    }
}
