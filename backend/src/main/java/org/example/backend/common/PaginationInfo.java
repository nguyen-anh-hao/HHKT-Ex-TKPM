package org.example.backend.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PaginationInfo {
    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;

    public PaginationInfo(Page<?> page) {
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
        this.totalItems = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
