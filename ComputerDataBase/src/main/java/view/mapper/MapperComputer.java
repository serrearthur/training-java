package view.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dao.DAOFactory;
import model.Computer;
import view.dto.DTOComputer;

public class MapperComputer {
    public static DTOComputer toDTOComputer(Computer c) {
        DTOComputer ret = new DTOComputer();
        ret.setId(c.getId().toString());
        ret.setName(c.getName());
        try {
            ret.setIntroduced(c.getIntroduced().toLocalDate().toString());
        } catch (NullPointerException e) {
            ret.setIntroduced(null);
        }
        try {
            ret.setDiscontinued(c.getDiscontinued().toLocalDate().toString());
        } catch (NullPointerException e) {
            ret.setDiscontinued(null);
        }
        try {
            ret.setCompany(DAOFactory.getInstance().getCompanyDao().getFromId(c.getCompanyId()).get(0).getName());
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            ret.setCompany(null);
        }
        try {
            ret.setCompanyId(c.getCompanyId().toString());
        } catch (NullPointerException e) {
            ret.setCompanyId(null);
        }
        return ret;
    }
    
    public static Computer toComputer(DTOComputer c) {
        Computer ret = new Computer();
        ret.setId(Integer.parseInt(c.getId()));
        ret.setName(c.getName());
        try {
            ret.setIntroduced(LocalDateTime.of(LocalDate.parse(c.getIntroduced()), LocalTime.of(0, 0)));
        } catch (NullPointerException e) {
            ret.setIntroduced(null);
        }
        try {
            ret.setDiscontinued(LocalDateTime.of(LocalDate.parse(c.getDiscontinued()), LocalTime.of(0, 0)));
        } catch (NullPointerException e) {
            ret.setDiscontinued(null);
        }
        try {
            ret.setCompanyId(Integer.parseInt(c.getCompanyId()));
        } catch (NumberFormatException e) {
            ret.setCompanyId(null);
        }
        return ret;
    }
    
    public static List<DTOComputer> toDTOComputer(List<Computer> l) {
        List<DTOComputer> ret = new ArrayList<DTOComputer>();
        for (Computer c : l) {
            ret.add(toDTOComputer(c));
        }
        return ret;
    }
    
    public static List<Computer> toComputer(List<DTOComputer> l) {
        List<Computer> ret = new ArrayList<Computer>();
        for (DTOComputer c : l) {
            ret.add(toComputer(c));
        }
        return ret;
    }
}
