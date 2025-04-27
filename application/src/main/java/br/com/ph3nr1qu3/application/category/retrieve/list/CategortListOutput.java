package br.com.ph3nr1qu3.application.category.retrieve.list;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryID;

import java.time.Instant;

public record CategortListOutput(
        String name, String description, boolean isActive,
        Instant createdAt, Instant updatedAt, Instant deletedAt, CategoryID id
) {

    public static CategortListOutput from(final Category category) {
        return new CategortListOutput(
                category.getName(), category.getDescription(), category.isActive(), category.getCreatedAt(), category.getUpdatedAt(), category.getDeletedAt(), category.getId()
        );
    }
}
