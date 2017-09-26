package view.mapper;

import java.util.ArrayList;
import java.util.List;

import model.Company;
import view.dto.DTOCompany;

public class MapperCompany {
    public static DTOCompany toDtoCompany(Company c) {
        DTOCompany ret = new DTOCompany();
        ret.setId(c.getId().toString());
        ret.setName(c.getName());
        return ret;
    }

    public static Company toCompany(DTOCompany c) {
        Company ret = new Company();
        ret.setId(Integer.parseInt(c.getName()));
        ret.setName(c.getName());
        return ret;
    }

    public static List<DTOCompany> toDTOCompany(List<Company> l) {
        List<DTOCompany> ret = new ArrayList<DTOCompany>();
        for (Company c : l) {
            ret.add(toDtoCompany(c));
        }
        return ret;
    }

    public static List<Company> toCompany(List<DTOCompany> l) {
        List<Company> ret = new ArrayList<Company>();
        for (DTOCompany c : l) {
            ret.add(toCompany(c));
        }
        return ret;
    }
}
