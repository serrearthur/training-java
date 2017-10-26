package cdb.view.mapper;

import java.util.ArrayList;
import java.util.List;

import cdb.model.Company;
import cdb.view.dto.DTOCompany;

/**
 * Mapper allowing to map {@link DTOCompany} and {@link Company}.
 * @author aserre
 */
public class MapperCompany {
    /**
     * Function converting a {@link Company} into a {@link DTOCompany}.
     * @param c original {@link Company}
     * @return result {@link DTOCompany}
     */
    public static DTOCompany toDtoCompany(Company c) {
        DTOCompany ret = new DTOCompany();
        ret.setId(c.getId().toString());
        ret.setName(c.getName());
        return ret;
    }

    /**
     * Function converting a {@link DTOCompany} into a {@link Company}.
     * @param c original {@link DTOCompany}
     * @return result {@link Company}
     */
    public static Company toCompany(DTOCompany c) {
        Company ret = new Company();
        ret.setId(Integer.parseInt(c.getName()));
        ret.setName(c.getName());
        return ret;
    }


    /**
     * Function converting a list of {@link Company} into a list of {@link DTOCompany}.
     * @param l original list of {@link Company}
     * @return result list of {@link DTOCompany}
     */
    public static List<DTOCompany> toDTOCompany(List<Company> l) {
        List<DTOCompany> ret = new ArrayList<DTOCompany>();
        for (Company c : l) {
            ret.add(toDtoCompany(c));
        }
        return ret;
    }

    /**
     * Function converting a list of {@link DTOCompany} into a list of {@link Company}.
     * @param l original list of {@link DTOCompany}
     * @return result list of {@link Company}
     */
    public static List<Company> toCompany(List<DTOCompany> l) {
        List<Company> ret = new ArrayList<Company>();
        for (DTOCompany c : l) {
            ret.add(toCompany(c));
        }
        return ret;
    }
}
