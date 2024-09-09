package br.com.ph3nr1qu3.core.pagination;

import java.util.List;

public record Pagination<T>(
        int currentPage, int perPage, long total, List<T> items
) {
}
