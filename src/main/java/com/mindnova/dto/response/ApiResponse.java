package com.mindnova.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private Pagination pagination;

    @Data
    @AllArgsConstructor
    public static class Pagination {
        private int page;
        private int limit;
        private long totalItems;
        private int totalPages;
    }
}