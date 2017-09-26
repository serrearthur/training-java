package view;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    private List<T> data;
    private int limit;
    private int totalPage;
    private int currentPageNumber;
    private static final int DISPLAY_PAGE_BORDERS = 4;

    public Page(List<T> data, int limit) {
        this.data = new ArrayList<T>();
        this.data.addAll(data);
        this.limit = limit;
        this.currentPageNumber = 1;
        this.totalPage = 1 + this.data.size() / this.limit;
    }

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

    public int getNextPageNumber() {
        if (this.currentPageNumber < this.totalPage) {
            return this.currentPageNumber + 1;
        } else {
            return this.currentPageNumber;
        }
    }

    public int getPreviousPageNumber() {
        if (this.currentPageNumber > 1) {
            return this.currentPageNumber - 1;
        } else {
            return this.currentPageNumber;
        }
    }

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

    public int getDisplayMin() {
        if (this.currentPageNumber + DISPLAY_PAGE_BORDERS > this.totalPage) {
            return Math.max(this.totalPage - 2 * DISPLAY_PAGE_BORDERS, 1);
        } else {
            return Math.max(this.currentPageNumber - DISPLAY_PAGE_BORDERS, 1);
        }
    }

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

    public void setLimit(int i) {
        this.limit = i;
        this.totalPage = 1 + this.data.size() / this.limit;
        this.currentPageNumber = Math.min(this.currentPageNumber, this.totalPage);
    }

    public int getTotalCount() {
        return data.size();
    }
}
