package cdb.controller.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cdb.controller.servlet.fields.ComputerFields;
import cdb.model.Computer;

/**
 * Class designed to assess wether data input from the user respects the format for {@link Computer}.
 * @author aserre
 */
public class ComputerValidator implements ComputerFields {
    /**
     * Method containing a succession of tests to validate its input parameters.
     * @param id ID to validate
     * @param name Name to validate
     * @param introduced Introduced date to validate
     * @param discontinued Discontinued date to validate
     * @param companyId companyId to validate
     * @param errors array where error messages are stored
     * @return a properly formated {@link Computer} object
     */
    public static Computer validate(String id, String name, String introduced, String discontinued, String companyId,
            Map<String, String> errors) {
        Computer c = validate(name, introduced, discontinued, companyId, errors);
        c.setId(validationId(id, errors));
        return c;
    }

    /**
     * Method containing a succession of tests to validate its input parameters.
     * @param name Name to validate
     * @param introduced Introduced date to validate
     * @param discontinued Discontinued date to validate
     * @param companyId companyId to validate
     * @param errors array where error messages are stored
     * @return a properly formated {@link Computer} object
     */
    public static Computer validate(String name, String introduced, String discontinued, String companyId,
            Map<String, String> errors) {
        Computer c = new Computer();
        c.setName(validationName(name, errors));
        c.setIntroduced(validationIntroduced(introduced, errors));
        c.setDiscontinued(validationDiscontinued(discontinued, errors));
        if (!(c.getIntroduced() == null || c.getDiscontinued() == null
                || c.getDiscontinued().isEqual(c.getIntroduced())
                || c.getDiscontinued().isAfter(c.getIntroduced()))) {
            errors.put(ATT_DISCONTINUED, "Discontinued date must be later than Introduced date.");
            c.setDiscontinued(null);
        }
        c.setCompanyId(validationCompanyId(companyId, errors));
        return c;
    }

    /**
     * Return an id in the {@link Integer} format if the input is valid,
     * returns null otherwise.
     * @param id ID to validate
     * @param errors map of error messages
     * @return parsed id
     */
    private static Integer validationId(String id, Map<String, String> errors) {
        if (id == null || id.isEmpty() || id.equals("0")) {
            errors.put(ATT_COMPUTERID, "Id can't be empty or equal to 0.");
            return null;
        } else {
            Pattern p = Pattern.compile("^[1-9][0-9]*$");
            Matcher m = p.matcher(id);
            if (m.find()) {
                return Integer.parseInt(id);
            } else {
                errors.put(ATT_COMPANYID, "Unable to parse Id.");
                return null;
            }
        }
    }

    /**
     * Return a name as a String if the input is valid,
     * returns null otherwise.
     * @param name name to validate
     * @param errors map of error messages
     * @return valid name
     */
    private static String validationName(String name, Map<String, String> errors) {
        if (name == null || name.isEmpty()) {
            errors.put(ATT_NAME, "Name can't be empty.");
            return null;
        } else {
            return name;
        }
    }

    /**
     * Return an introduced date in the {@link LocalDateTime} format if the input is valid,
     * returns null otherwise.
     * @param introduced introduced date to validate
     * @param errors map of error messages
     * @return valid introduced date
     */
    private static LocalDateTime validationIntroduced(String introduced, Map<String, String> errors) {
        LocalDateTime ret = null;
        if (introduced != null && !introduced.isEmpty()) {
            try {
                ret = LocalDateTime.of(LocalDate.parse(introduced), LocalTime.of(0, 0));
            } catch (DateTimeParseException e) {
                errors.put(ATT_INTRODUCED, "Invalid date format.");
            }
        }
        return ret;
    }

    /**
     * Return an introduced date in the {@link LocalDateTime} format if the input is valid,
     * returns null otherwise.
     * @param discontinued discontinued date to validate
     * @param errors map of error messages
     * @return valid discontinued date
     */
    private static LocalDateTime validationDiscontinued(String discontinued, Map<String, String> errors) {
        LocalDateTime ret = null;
        if (discontinued != null && !discontinued.isEmpty()) {
            try {
                ret = LocalDateTime.of(LocalDate.parse(discontinued), LocalTime.of(0, 0));
            } catch (DateTimeParseException e) {
                errors.put(ATT_DISCONTINUED, "Invalid date format.");
            }
        }
        return ret;
    }

    /**
     * Return a companyId in the {@link Integer} format if the input is valid,
     * returns null otherwise.
     * @param companyId companyId to validate
     * @param errors map of error messages
     * @return valid companyId
     */
    private static Integer validationCompanyId(String companyId, Map<String, String> errors) {
        if (companyId == null | companyId.isEmpty()) {
            return null;
        } else {
            Pattern p = Pattern.compile("^[0-9]+$");
            Matcher m = p.matcher(companyId);
            if (m.find()) {
                Integer ret = Integer.parseInt(companyId);
                if (ret == 0) {
                    ret = null;
                }
                return ret;
            } else {
                errors.put(ATT_COMPANYID, "Invalid company identifier.");
                return null;
            }
        }
    }
}
