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
    private static final int DISPLAY_PAGE_BORDERS = 4;

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
     * Gives the number of the next page, takes care of overflow.
     * @return index of the next page
     */
    public int getNextPageNumber() {
        if (this.currentPageNumber < this.totalPage) {
            return this.currentPageNumber + 1;
        } else {
            return this.currentPageNumber;
        }
    }

    /**
     * Gives the previous page number, takes care of underflow.
     * @return index of the previous page.
     */
    public int getPreviousPageNumber() {
        if (this.currentPageNumber > 1) {
            return this.currentPageNumber - 1;
        } else {
            return this.currentPageNumber;
        }
    }

    /**
     * Sets {@link Page#currentPageNumber} to a specific value.
     * Checks for overflow and underflow.
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

    /**
     * Method required to configure pagination.
     * Returns the minimal index to display in the pagination.
     * @return Minimal index to display
     */
    public int getDisplayMin() {
        if (this.currentPageNumber + DISPLAY_PAGE_BORDERS > this.totalPage) {
            return Math.max(this.totalPage - 2 * DISPLAY_PAGE_BORDERS, 1);
        } else {
            return Math.max(this.currentPageNumber - DISPLAY_PAGE_BORDERS, 1);
        }
    }

    /**
     * Method required to configure pagination.
     * Returns the maximal index to display in the pagination.
     * @return Maximal index to display
     */
    public int getDisplayMax() {
        if (currentPageNumber - DISPLAY_PAGE_BORDERS <= 0) {
            return Math.min(1 + 2 * DISPLAY_PAGE_BORDERS, this.totalPage);
        } else {
            return Math.min(this.currentPageNumber + DISPLAY_PAGE_BORDERS, this.totalPage);
        }
    }

    public int getLimit() {
        return this.limit;
    }

    /**
     * Sets the maximum number of item per page,
     * and  consequently update the value of {@link Page#totalPage} and {@link Page#currentPageNumber}.
     * @param i new maximum number of item per page
     */
    public void setLimit(int i) {
        this.limit = i;
        this.totalPage = 1 + this.data.size() / this.limit;
        this.currentPageNumber = Math.min(this.currentPageNumber, this.totalPage);
    }

    public int getTotalCount() {
        return data.size();
    }
}
