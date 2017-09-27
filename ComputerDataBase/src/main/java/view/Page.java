package view;

import java.util.ArrayList;
import java.util.List;

/**
 * List of data represented as a page in order to be displayed.
 * @author aserre
 * @param <T> generic type
 */
public class Page<T> {
    private List<T> data;
    private int limit;
    private int totalPage;
    private int currentPageNumber;
    private int pageBorders;

    /**
     * Constructor.
     * @param data data to be represented as a page
     * @param limit number of entries per page
     */
    public Page(List<T> data, int limit) {
        this.data = new ArrayList<T>();
        this.data.addAll(data);
        this.limit = limit;
        this.currentPageNumber = 1;
        this.totalPage = 1 + this.data.size() / this.limit;
        this.pageBorders = 4;
    }

    /**
     * Selects only the part of the data corresponding to a specific page.
     * @param nb Page number to select
     * @return selected data
     */
    public List<T> getByPageNumber(int nb) {
        List<T> ret = new ArrayList<T>();
        if (nb > 0 && nb <= this.totalPage) {
            for (int i = (nb - 1) * this.limit; i < nb * this.limit; i++) {
                if (i >= this.data.size()) {
                    break;
                }
                ret.add(this.data.get(i));
            }
        }
        return ret;
    }

    public List<T> getCurrentPage() {
        return getByPageNumber(this.currentPageNumber);
    }

    /**
     * Sets {@link Page#currentPageNumber} to a specific value. Checks for overflow
     * and underflow.
     * @param nb index of the new page
     * @return current page number, after affectation.
     */
    public int moveToPageNumber(int nb) {
        if (nb > 0 && nb <= this.totalPage) {
            this.currentPageNumber = nb;
        }
        return this.currentPageNumber;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public int getCurrentPageNumber() {
        return this.currentPageNumber;
    }

    public int getTotalCount() {
        return data.size();
    }

    public int getLimit() {
        return this.limit;
    }

    /**
     * Sets the maximum number of item per page, and consequently updates the value
     * of {@link Page#totalPage} and {@link Page#currentPageNumber}.
     * @param i new maximum number of item per page
     */
    public void setLimit(int i) {
        this.limit = i;
        this.totalPage = 1 + this.data.size() / this.limit;
        this.currentPageNumber = Math.min(this.currentPageNumber, this.totalPage);
    }

    public int getPageBorders() {
        return this.pageBorders;
    }

    public void setPageBorders(int pageBorders) {
        this.pageBorders = pageBorders;
    }
}
