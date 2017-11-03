package cdb.view.fields;

/**
 * Interface containing the general names of the fields used in the jsp.
 * @author aserre
 */
public interface GeneralFields {
    String ATT_ERRORS = "errors";
    String ATT_COMPANIES = "companies";
    String ATT_FIELDS = "fields";
    String VIEW_HOME = "/home";

    default String getAttErrors() {
        return ATT_ERRORS;
    }

    default String getAttCompanies() {
        return ATT_COMPANIES;
    }

    default String getAttFields() {
        return ATT_FIELDS;
    }
    
    default String getViewHome() {
        return VIEW_HOME;
    }
}