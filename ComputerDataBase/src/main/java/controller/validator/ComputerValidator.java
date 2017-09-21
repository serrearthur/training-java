package controller.validator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import model.Computer;

public class ComputerValidator {
    public static Computer validate(String name, String introduced, String discontinued, String companyId,
            List<String> errors) {
        Computer c = new Computer();
        try {
            c.setName(validationName(name));
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        try {
            c.setIntroduced(validationIntroduced(introduced));
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        try {
            c.setDiscontinued(validationDiscontinued(discontinued));
            if (!(c.getIntroduced() == null || c.getDiscontinued().isEqual(c.getIntroduced())
                    || c.getDiscontinued().isAfter(c.getIntroduced()))) {
                throw new Exception("Discontinued date must be later than Introduced date.");
            }
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        try {
            c.setCompanyId(validationCompanyId(companyId));
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        return c;
    }

    private static String validationName(String name) throws Exception {
        if (name == null || name.trim().length() == 0) {
            throw new Exception("Name can't be empty.");
        } else {
            return name;
        }
    }

    private static LocalDateTime validationIntroduced(String introduced) throws Exception {
        if (introduced == null || introduced.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.of(LocalDate.parse(introduced), null);
        } catch (DateTimeParseException e) {
            throw new Exception("Invalid date format.");
        }
    }

    private static LocalDateTime validationDiscontinued(String discontinued) throws Exception {
        if (discontinued == null || discontinued.isEmpty()) {
            return null;
        }
        try {
            return LocalDateTime.of(LocalDate.parse(discontinued), null);
        } catch (DateTimeParseException e) {
            throw new Exception("Invalid date format.");
        }
    }

    private static Integer validationCompanyId(String companyId) throws Exception {
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
            throw new Exception("Invalid identifier");
        }
    }
}
