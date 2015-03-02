package com.ktruong.googleimagesearcher.client;

import java.io.Serializable;

/**
 * Created by ktruong on 2/27/15.
 */
public class SearchQuery implements Serializable {
    private String query;
    private int start;
    private int size;

    public SearchQuery(String query, int start) {
        this.query = query;
        this.start = start;
        this.size = 8;
    }

    public int getSize() {
        return size;
    }

    /**
     * google max is 8 per page 
     * @param size
     */
    public void setSize(int size) {
        this.size = (size>8) ? 8 : size;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchQuery{");
        sb.append("query='").append(query).append('\'');
        sb.append(", start=").append(start);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
