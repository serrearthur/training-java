package cdb.view;

import java.util.ArrayList;
import java.util.List;

import cdb.view.fields.AllFields;

/**
 * List of data represented as a page in order to be displayed.
 * @author aserre
 * @param <T> generic type
 */
public class Page<T> implements AllFields {
    private static final int DEFAULT_BORDER_SIZE = 4;

    private List<T> data;
    private int limit;
    private int totalPage;
    private int pageNb;
    private int paginationBorders;
    private int totalCount;
    private String col;
    private String order;
    private String search;

    /**
     * Constructor.
     * @param data data representing a page of a request
     * @param pageNb the current page
     * @param totalCount total size of a request
     * @param col column to order by
     * @param order "ASC" or "DESC"
     * @param limit number of entries per page
     * @param search string searched for this page
     */
    public Page(List<T> data, int pageNb, int limit, int totalCount, String col, String order, String search) {
        this.data = new ArrayList<T>(data);
        this.limit = limit;
        this.pageNb = pageNb;
        this.totalCount = totalCount;
        this.col = col;
        this.order = order;
        this.search = search;
        this.totalPage = 1 + totalCount / this.limit;
        this.paginationBorders = DEFAULT_BORDER_SIZE;
    }

    /**
     * Configure the input stream to invert the sorting order.
     * <p>
     * If the input is different from the stored column, return the input.
     * Otherwise, return the inversion of the stored value.
     * @param col New column to sort by
     * @return the exact value format for the sort
     */
    public String getNewOrder(String col) {
        if (this.col.equals(col)) {
            if (this.order.equals("ASC")) {
                return "DESC";
            } else {
                return "ASC";
            }
        } else {
            return "ASC";
        }
    }

    public List<T> getData() {
        return this.data;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public int getPageNb() {
        return this.pageNb;
    }

    /**
     * Sets {@link Page#pageNb} to a specific value. Checks for overflow
     * and underflow.
     * @param nb index of the new page
     * @return current page number, after affectation.
     */
    public int setPageNb(int nb) {
        if (nb > 0 && nb <= this.totalPage) {
            this.pageNb = nb;
        }
        return this.pageNb;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public int getLimit() {
        return this.limit;
    }

    /**
     * Sets the maximum number of item per page, and consequently updates the value
     * of {@link Page#totalPage} and {@link Page#pageNb}.
     * @param i new maximum number of item per page
     */
    public void setLimit(int i) {
        this.limit = i;
        this.totalPage = 1 + this.totalCount / this.limit;
        this.pageNb = Math.min(this.pageNb, this.totalPage);
    }

    public int getPaginationBorders() {
        return this.paginationBorders;
    }

    public void setPaginationBorders(int paginationBorders) {
        this.paginationBorders = paginationBorders;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getCol() {
        return this.col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
