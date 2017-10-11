package controller.service;

import java.util.List;

import dao.ConnectionManager;
import dao.DAOCompany;
import dao.exceptions.DAOException;
import dao.impl.DAOCompanyImpl;
import dao.impl.DAOComputerImpl;
import model.Company;
import view.dto.DTOCompany;
import view.mapper.MapperCompany;

/**
 * Service providing an interface between the servlet and the Company DAO.
 * @author aserre
 */
public class ServiceCompany {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceCompany.class);
    private DAOCompany dao;
    private ConnectionManager manager;

    /**
     * Initialization-on-demand singleton holder  for {@link ServiceCompany}.
     */
    private static class SingletonHolder {
        private static final ServiceCompany INSTANCE = new ServiceCompany();
    }

    /**
     * Accessor for the instance of the singleton.
     * @return the instance of {@link ServiceCompany}
     */
    public static ServiceCompany getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Contructor for a new ServiceCompany.
     */
    private ServiceCompany() {
        this.dao = DAOCompanyImpl.getInstance();
        this.manager = ConnectionManager.getInstance();
    }

    /**
     * Returns a list of all the companies inside the database.
     * @return a list of companies in {@link DTOCompany} format
     */
    public List<DTOCompany> getCompanies() {
        List<DTOCompany> ret = null;
        try {
            List<Company> l = dao.getAll();
            ret = MapperCompany.toDTOCompany(l);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
        return ret;
    }

    /**
     * Delete a {@link Company} and all the computer it contains.
     * @param companyId company to be deleted
     */
    public void deleteCompany(Integer companyId) {
        try {
            manager.setAutoCommit(false);
            DAOComputerImpl.getInstance().deleteCompanyId(companyId.toString());
            dao.delete(companyId);
            manager.commit();
        } catch (DAOException e) {
            logger.error(e.getMessage() + " : rolling back.");
            manager.rollback();
        } finally {
            try {
                manager.closeConnection();
            } catch (DAOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
