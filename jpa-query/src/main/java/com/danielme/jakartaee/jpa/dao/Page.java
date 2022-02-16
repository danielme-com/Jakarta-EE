package com.danielme.jakartaee.jpa.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Page<T> {

    private final List<T> results;
    private final long total;
    private final int firstPosition;
    private final int pageSize;

    public int getNumPages() {
        return total == 0 ? 0 : (int) Math.ceil((double) this.total / (double) pageSize);
    }

    public int getNumber() {
        return results.size() == 0 ? 0 : (int) Math.ceil((firstPosition + 1.0) / pageSize);
    }

    public boolean hasNext() {
        return getNumber() > 0 && getNumber() < getNumPages();
    }

}
