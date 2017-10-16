package cdb.controller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cdb.dao.ConnectionManager;
import cdb.dao.DAOCompany;
import cdb.dao.DAOComputer;
import cdb.dao.exceptions.DAOException;
import cdb.model.Company;
import cdb.view.dto.DTOCompany;
import cdb.view.mapper.MapperCompany;

/**
 * Service providing an interface between the servlet and the Company DAO.
 * @author aserre
 */
@Component
public class ServiceCompany {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceCompany.class);
    @Autowired
    private DAOComputer daoComputer;
    @Autowired
    private DAOCompany daoCompany;
    @Autowired
    private ConnectionManager manager;

    /**
     * Contructor for a new ServiceCompany.
     */
    public ServiceCompany() {
    }

    public DAOComputer getDaoComputer() {
        return this.daoComputer;
    }

    public void setDaoComputer(DAOComputer daoComputer) {
        this.daoComputer = daoComputer;
    }

    public DAOCompany getDaoCompany() {
        return this.daoCompany;
    }

    public void setDaoCompany(DAOCompany daoCompany) {
        this.daoCompany = daoCompany;
    }

    public ConnectionManager getManager() {
        return this.manager;
    }

    public void setManager(ConnectionManager manager) {
        this.manager = manager;
    }


    /**
     * Returns a list of all the companies inside the database.
     * @return a list of companies in {@link DTOCompany} format
     */
    public List<DTOCompany> getCompanies() {
        List<DTOCompany> ret = null;
        try {
            List<Company> l = daoCompany.getAll();
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
            daoComputer.deleteCompanyId(companyId.toString());
            daoCompany.delete(companyId);
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
