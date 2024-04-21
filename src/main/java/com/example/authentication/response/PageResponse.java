package com.example.authentication.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> content;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
    private Integer pageSize;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.currentPage = page.getNumber() + 1;
        this.pageSize = page.getPageable().getPageSize();
    }

    public PageResponse(
            List<T> contents,
            Integer totalPages,
            Long totalElements,
            Integer currentPage,
            Integer pageSize
    ) {
        this.content = contents;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.currentPage = currentPage + 1;
        this.pageSize = pageSize;
    }

    public PageResponse() {
        this.content = new ArrayList<>();
        this.totalPages = 0;
        this.totalElements = 0L;
        this.currentPage = 0;
        this.pageSize = 0;
    }

}
