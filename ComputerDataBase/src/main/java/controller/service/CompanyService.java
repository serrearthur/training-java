package controller.service;

import java.util.List;

import dao.DAOFactory;
import view.dto.DTOCompany;
import view.mapper.MapperCompany;

/**
 * Service providing an interface between the servlet and the Company DAO.
 * @author aserre
 */
public class CompanyService {
    private static final DAOFactory FACTORY = DAOFactory.getInstance();

    /**
     * Returns a list of all the companies inside the database.
     * @return a list of companies in {@link DTOCompany} format
     */
    public static List<DTOCompany> getCompanies() {
        return MapperCompany.toDTOCompany(FACTORY.getCompanyDao().getAll());
    }

}
