package com.javatechie.jwt.api.dto;

import java.util.List;

public class PagedResponse<T> {
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final List<T> content;

    public PagedResponse(int page, int size, long totalElements, int totalPages, List<T> content) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getContent() {
        return content;
    }
}
