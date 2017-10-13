package cdb.controller.servlet.fields;

/**
 * Interface containing the name of the fields used in the jsp for the Computers.
 * @author aserre
 */
public interface ComputerFields {
    String ATT_COMPUTERID = "id";
    String ATT_NAME = "name";
    String ATT_INTRODUCED = "introduced";
    String ATT_DISCONTINUED = "discontinued";
    String ATT_COMPANYID = "companyId";
    String ATT_COMPUTER = "computer";

    default String getAttComputerId() {
        return ATT_COMPUTERID;
    }

    default String getAttName() {
        return ATT_NAME;
    }

    default String getAttIntroduced() {
        return ATT_INTRODUCED;
    }

    default String getAttDiscontinued() {
        return ATT_DISCONTINUED;
    }

    default String getAttCompanyId() {
        return ATT_COMPANYID;
    }

    default String getAttComputer() {
        return ATT_COMPUTER;
    }
}