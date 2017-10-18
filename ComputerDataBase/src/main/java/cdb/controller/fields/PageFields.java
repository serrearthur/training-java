package cdb.controller.fields;

/**
 * Interface containing the name of the fields used in the jsp for the Pages.
 * @author aserre
 */
public interface PageFields {
    String ATT_PAGE = "page";
    String ATT_PAGELIMIT = "limit";
    String ATT_PAGENUMBER = "pageNb";
    String ATT_SEARCH = "search";
    String ATT_DELETE = "selection";
    String ATT_COL = "col";
    String ATT_ORDER = "order";

    default String getAttPage() {
        return ATT_PAGE;
    }

    default String getAttPageLimit() {
        return ATT_PAGELIMIT;
    }

    default String getAttPageNumber() {
        return ATT_PAGENUMBER;
    }

    default String getAttSearch() {
        return ATT_SEARCH;
    }

    default String getAttDelete() {
        return ATT_DELETE;
    }

    default String getAttCol() {
        return ATT_COL;
    }

    default String getAttOrder() {
        return ATT_ORDER;
    }
}
