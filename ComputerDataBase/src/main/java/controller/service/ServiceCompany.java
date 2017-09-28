package controller.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ConnectionManager;
import dao.exceptions.DAOConfigurationException;
import dao.exceptions.DAOException;
import dao.impl.DAOCompany;
import dao.impl.DAOComputer;
import model.Company;
import view.dto.DTOCompany;
import view.mapper.MapperCompany;

/**
 * Service providing an interface between the servlet and the Company DAO.
 * @author aserre
 */
public class ServiceCompany {
    /**
     * Returns a list of all the companies inside the database.
     * @return a list of companies in {@link DTOCompany} format
     */
    public static List<DTOCompany> getCompanies() {
        List<DTOCompany> ret = null;
        try {
            List<Company> l = DAOCompany.getInstance().getAll();
            ret = MapperCompany.toDTOCompany(l);
            ConnectionManager.getInstance().closeConnection();
        } catch (DAOConfigurationException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Delete a {@link Company} and all the computer it contains.
     * @param companyId company to be deleted
     */
    public static void deleteCompany(Integer companyId) {
        Connection connection = ConnectionManager.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            DAOCompany.getInstance().delete(companyId);
            DAOComputer.getInstance().deleteCompanyId(companyId);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DAOException(e);
            } catch (SQLException ex) {
                throw new DAOException(ex);
            }
        } finally {
            try {
                ConnectionManager.getInstance().closeConnection();
            } catch (DAOConfigurationException e) {
                throw new DAOException(e);
            }
        }
    }
}
