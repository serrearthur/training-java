package controller.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.validation.ComputerValidator;
import dao.ConnectionManager;
import dao.exceptions.DAOConfigurationException;
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
    /**
     * Gets the current page from the database.
     * @param limit The maximum number of Computer per page
     * @return a list of computers in {@link DTOComputer} format
     */
    public static Page<DTOComputer> getPage(int limit) {
        Page<DTOComputer> ret = null;
        try {
            List<Computer> l = DAOComputer.getInstance().getAll();
            ret = new Page<DTOComputer>(MapperComputer.toDTOComputer(l), limit);
            ConnectionManager.getInstance().closeConnection();
        } catch (DAOConfigurationException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Creates a page of computers from the result of the search request.
     * @param search The search criterias
     * @param limit The maximum number of Computer per page
     * @return a list of computers in {@link DTOComputer} format
     */
    public static Page<DTOComputer> getPage(String search, int limit) {
        Page<DTOComputer> ret = null;
        try {
            List<Computer> l = DAOComputer.getInstance().getFromName(search);
            ret = new Page<DTOComputer>(MapperComputer.toDTOComputer(l), limit);
            ConnectionManager.getInstance().closeConnection();
        } catch (DAOConfigurationException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Delete the requested computers from the database.
     * @param requestedDelete ID of the computers to delete, spearated by a comma
     */
    public static void delete(String requestedDelete) {
        for (String s : requestedDelete.split(",")) {
            DAOComputer.getInstance().delete(Integer.parseInt(s));
        }
        try {
            ConnectionManager.getInstance().closeConnection();
        } catch (DAOConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a specific computer from the database.
     * @param computerID ID of the computer we want to access
     * @return a computers in {@link DTOComputer} format
     */
    public static DTOComputer getComputer(String computerID) {
        DTOComputer c = new DTOComputer();
        try {
            Computer comp = DAOComputer.getInstance().getFromId(Integer.parseInt(computerID)).get(0);
            ConnectionManager.getInstance().closeConnection();
            c = MapperComputer.toDTOComputer(comp);
            ConnectionManager.getInstance().closeConnection();
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println("Computer \"" + computerID + "\" not found : " + e.getMessage());
        } catch (DAOConfigurationException e) {
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
    public static Map<String, String> addComputer(String name, String introduced, String discontinued, String companyId) {
        Map<String, String> errors = new HashMap<String, String>();
        Computer c = ComputerValidator.validate(name, introduced, discontinued, companyId, errors);
        if (errors.isEmpty()) {
            DAOComputer.getInstance().create(c);
            try {
                ConnectionManager.getInstance().closeConnection();
            } catch (DAOConfigurationException e) {
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
    public static Map<String, String> editComputer(String id, String name, String introduced, String discontinued,
            String companyId) {
        Map<String, String> errors = new HashMap<String, String>();
        Computer c = ComputerValidator.validate(id, name, introduced, discontinued, companyId, errors);
        if (errors.isEmpty()) {
            DAOComputer.getInstance().update(c);
            try {
                ConnectionManager.getInstance().closeConnection();
            } catch (DAOConfigurationException e) {
                e.printStackTrace();
            }
        }
        return errors;
    }
}
