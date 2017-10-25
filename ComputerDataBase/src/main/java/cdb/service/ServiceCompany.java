package cdb.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<DTOCompany> getCompanies() {
        List<DTOCompany> ret = null;
        try {
            List<Company> l = daoCompany.findAll();
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
            daoComputer.deleteByCompanyId(companyId);
            daoCompany.deleteById(companyId);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
    }
}
