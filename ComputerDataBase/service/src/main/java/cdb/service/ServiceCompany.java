package cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cdb.model.Company;
import cdb.persistence.DAOCompany;
import cdb.persistence.DAOComputer;

/**
 * Service providing an interface between the servlet and the Company DAO.
 * @author aserre
 */
@Service
@Transactional
public class ServiceCompany {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceCompany.class);
    private DAOComputer daoComputer;
    private DAOCompany daoCompany;

    /**
     * Constructor.
     * @param daoCompany {@link DaoCompany} bean
     * @param daoComputer {@link DaoComputer} bean
     */
    @Autowired
    public ServiceCompany(DAOCompany daoCompany, DAOComputer daoComputer) {
        this.daoCompany = daoCompany;
        this.daoComputer = daoComputer;
    }

    /**
     * Returns a list of all the companies inside the database.
     * @return a list of companies in {@link DTOCompany} format
     */
    @Transactional(readOnly = true)
    public List<Company> getCompanies() {
        try {
            return daoCompany.findAll();
        } catch (RuntimeException e) {
            logger.error("Company Service - Error during getCompanies : ", e);
            throw e;
        }
    }
    
    /**
     * Returns the company corresponding to the id.
     * @param id the id of the company
     * @return a companies in {@link DTOCompany} format
     */
    @Transactional(readOnly = true)
    public Company getCompany(Integer id) {
        try {
            return daoCompany.findById(id).get();
        } catch (RuntimeException e) {
            logger.error("Company Service - Error during getCompany : ", e);
            throw e;
        }
    }


    /**
     * Delete a {@link Company} and all the computer it contains.
     * @param companyId company to be deleted
     */
    public void deleteCompany(Integer companyId) {
        try {
            daoComputer.deleteByCompanyId(companyId);
            daoCompany.deleteById(companyId);
        } catch (RuntimeException e) {
            logger.error("Company Service - Error during deleteCompany : ", e);
            throw e;
        }
    }
}
