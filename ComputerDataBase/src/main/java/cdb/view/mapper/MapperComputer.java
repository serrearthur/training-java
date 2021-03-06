package cdb.view.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cdb.dao.exceptions.DAOException;
import cdb.dao.DAOCompany;
import cdb.model.Computer;
import cdb.view.dto.DTOComputer;

/**
 * Mapper allowing to map {@link DTOComputer} and {@link Computer}.
 * @author aserre
 */
public class MapperComputer {
    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MapperComputer.class);
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    /**
     * Function converting a {@link Computer} into a {@link DTOComputer}.
     * @param c original {@link Computer}
     * @return result {@link DTOComputer}
     */
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
            ret.setCompany(((DAOCompany) ctx.getBean("daoCompany")).getFromId(c.getCompanyId()).get(0).getName());
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            ret.setCompany(null);
        } catch (DAOException e) {
            logger.error(e.getMessage());
        }
        try {
            ret.setCompanyId(c.getCompanyId().toString());
        } catch (NullPointerException e) {
            ret.setCompanyId(null);
        }
        return ret;
    }

    /**
     * Function converting a {@link DTOComputer} into a {@link Computer}.
     * @param c original {@link DTOComputer}
     * @return result {@link Computer}
     */
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

    /**
     * Function converting a list of {@link Computer} into a list of {@link DTOComputer}.
     * @param l original list of {@link Computer}
     * @return result list of {@link DTOComputer}
     */
    public static List<DTOComputer> toDTOComputer(List<Computer> l) {
        List<DTOComputer> ret = new ArrayList<DTOComputer>();
        for (Computer c : l) {
            ret.add(toDTOComputer(c));
        }
        return ret;
    }

    /**
     * Function converting a list of {@link DTOComputer} into a list of {@link Computer}.
     * @param l original list of {@link DTOComputer}
     * @return result list of {@link Computer}
     */
    public static List<Computer> toComputer(List<DTOComputer> l) {
        List<Computer> ret = new ArrayList<Computer>();
        for (DTOComputer c : l) {
            ret.add(toComputer(c));
        }
        return ret;
    }
}