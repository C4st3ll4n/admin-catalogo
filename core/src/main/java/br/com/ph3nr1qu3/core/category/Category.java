package br.com.ph3nr1qu3.core.category;

import br.com.ph3nr1qu3.core.AggregateRoot;
import br.com.ph3nr1qu3.core.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {

    private final String name;

    private final String description;

    private final Boolean active;

    private final Instant createdAt;

    private final Instant deletedAt;

    private final Instant updatedAt;

    private Category(final CategoryID id,
                     final String name,
                     final String description,
                     final Boolean active,
                     final Instant createdAt,
                     final Instant deletedAt,
                     final Instant updatedAt) {
        super(id);
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
    }

    public static Category newCategory(
            final String aName,
            final String aDescription,
            final Boolean isActive
    ) {
        var now = Instant.now();
        return new Category(
                CategoryID.unique(),
                aName, aDescription, isActive,
                now, null, now
        );

    }

    public CategoryID getId() {
        return (CategoryID) id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }
}