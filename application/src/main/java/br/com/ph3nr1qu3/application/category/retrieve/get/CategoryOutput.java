package br.com.ph3nr1qu3.application.category.retrieve.get;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryID;

import java.time.Instant;

public record CategoryOutput(
        CategoryID id,
        String name,
        String description,
        boolean isActive,
        Instant createdAt,
        Instant deletedAt,
        Instant updatedAt
) {

    public static CategoryOutput from(Category cat) {
        return new CategoryOutput(
                cat.getId(),
                cat.getName(), cat.getDescription(),
                cat.isActive(), cat.getCreatedAt(), cat.getDeletedAt(), cat.getUpdatedAt()
        );
    }
}
