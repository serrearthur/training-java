package view.dto;

import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
import model.Computer;

public class DTOComputer {
    private String id;
    private String name;
    private String introduced;
    private String discontinued;
    private String company;
    private String companyId;

    public static List<DTOComputer> toDTOComputer(List<Computer> l) {
        List<DTOComputer> ret = new ArrayList<DTOComputer>();
        for (Computer c : l) {
            ret.add(new DTOComputer(c));
        }
        return ret;
    }

    public DTOComputer(Computer c) {
        this.id = c.getId().toString();
        this.name = c.getName();
        try {
            this.introduced = c.getIntroduced().toLocalDate().toString();
        } catch (NullPointerException e) {
            this.introduced = null;
        }
        try {
            this.discontinued = c.getDiscontinued().toLocalDate().toString();
        } catch (NullPointerException e) {
            this.discontinued = null;
        }
        try {
            this.company = DAOFactory.getInstance().getCompanyDao().getFromId(c.getCompanyId()).get(0).getName();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            this.company = null;
        }
        try {
            this.companyId = c.getCompanyId().toString();
        } catch (NullPointerException e) {
            this.companyId = null;
        }

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return this.introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return this.discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
