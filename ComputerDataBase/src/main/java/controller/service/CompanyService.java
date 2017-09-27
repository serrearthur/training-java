package controller.service;

import java.util.List;

import dao.impl.DAOCompany;
import view.dto.DTOCompany;
import view.mapper.MapperCompany;

/**
 * Service providing an interface between the servlet and the Company DAO.
 * @author aserre
 */
public class CompanyService {
    /**
     * Returns a list of all the companies inside the database.
     * @return a list of companies in {@link DTOCompany} format
     */
    public static List<DTOCompany> getCompanies() {
        return MapperCompany.toDTOCompany(DAOCompany.getInstance().getAll());
    }

}
