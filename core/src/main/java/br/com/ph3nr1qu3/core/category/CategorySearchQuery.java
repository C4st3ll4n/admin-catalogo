package br.com.ph3nr1qu3.core.category;

public record CategorySearchQuery(
        int page, int perPage, String terms, String sort, String direction
) {
}
