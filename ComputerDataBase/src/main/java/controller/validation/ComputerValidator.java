package controller.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

import controller.ComputerFields;
import model.Computer;

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
        try {
            c.setId(validationId(id));
        } catch (ValidationException e) {
            errors.put(ATT_COMPUTERID, e.getMessage());
        }
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
        try {
            c.setName(validationName(name));
        } catch (ValidationException e) {
            errors.put(ATT_NAME, e.getMessage());
        }

        try {
            c.setIntroduced(validationIntroduced(introduced));
        } catch (ValidationException e) {
            errors.put(ATT_INTRODUCED, e.getMessage());
        }

        try {
            c.setDiscontinued(validationDiscontinued(discontinued));
            if (!(c.getIntroduced() == null || c.getDiscontinued() == null
                    || c.getDiscontinued().isEqual(c.getIntroduced())
                    || c.getDiscontinued().isAfter(c.getIntroduced()))) {
                throw new ValidationException("Discontinued date must be later than Introduced date.");
            }
        } catch (ValidationException e) {
            errors.put(ATT_DISCONTINUED, e.getMessage());
        }

        try {
            c.setCompanyId(validationCompanyId(companyId));
        } catch (ValidationException e) {
            errors.put(ATT_COMPANYID, e.getMessage());
        }
        return c;
    }

    /**
     * Return an id in the {@link Integer} format if the input is valid,
     * throws a ValidationException otherwise.
     * @param id ID to validate
     * @return parsed id
     * @throws ValidationException thrown when the id is empty
     */
    private static Integer validationId(String id) throws ValidationException {
        if (id == null || id.isEmpty()) {
            throw new ValidationException("Id can't be empty.");
        }
        try {
            int i = Integer.parseInt(id);
            if (i == 0) {
                throw new NumberFormatException();
            } else {
                return i;
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Unable to parse Id.");
        }
    }

    /**
     * Return a name as a String if the input is valid,
     * throws a ValidationException otherwise.
     * @param name name to validate
     * @return valid name
     * @throws ValidationException thrown when the name is empty
     */
    private static String validationName(String name) throws ValidationException {
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Name can't be empty.");
        } else {
            return name;
        }
    }

    /**
     * Return an introduced date in the {@link LocalDateTime} format if the input is valid,
     * throws a ValidationException otherwise.
     * @param introduced introduced date to validate
     * @return valid introduced date
     * @throws ValidationException thrown when the introduced date is invalid
     */
    private static LocalDateTime validationIntroduced(String introduced) throws ValidationException {
        if (introduced == null || introduced.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.of(LocalDate.parse(introduced), LocalTime.of(0, 0));
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format.");
        }
    }

    /**
     * Return an introduced date in the {@link LocalDateTime} format if the input is valid,
     * throws a ValidationException otherwise.
     * @param discontinued discontinued date to validate
     * @return valid discontinued date
     * @throws ValidationException thrown when the discontinued date is invalid
     */
    private static LocalDateTime validationDiscontinued(String discontinued) throws ValidationException {
        if (discontinued == null || discontinued.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.of(LocalDate.parse(discontinued), LocalTime.of(0, 0));
        } catch (DateTimeParseException e) {
            throw new ValidationException("Invalid date format.");
        }
    }

    /**
     * Return a companyId in the {@link Integer} format if the input is valid,
     * throws a ValidationException otherwise.
     * @param companyId companyId to validate
     * @return valid companyId
     * @throws ValidationException thrown when the companyId is invalid
     */
    private static Integer validationCompanyId(String companyId) throws ValidationException {
        if (companyId == null || companyId.isEmpty()) {
            return null;
        }
        try {
            int i = Integer.parseInt(companyId);
            if (i == 0) {
                return null;
            } else {
                return i;
            }
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid identifier");
        }
    }
}
