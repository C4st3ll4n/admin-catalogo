package br.com.ph3nr1qu3.core.category;

import br.com.ph3nr1qu3.core.AggregateRoot;
import br.com.ph3nr1qu3.core.validation.ValidationHandler;

import java.time.Instant;
import java.util.Objects;

public class Category extends AggregateRoot<CategoryID> {

    private String name;

    private String description;
    private final Instant createdAt;
    private Boolean active;
    private Instant deletedAt;

    private Instant updatedAt;

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
                now, isActive ? null : now, now
        );

    }

    public CategoryID getId() {
        return id;
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

    public Category deactivate() {
        if (Objects.isNull(deletedAt)) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(final String newName, final String newDescription, final boolean newIsActive) {
        this.name = newName;
        this.description = newDescription;
        this.active = newIsActive;

        if(newIsActive){
            activate();
        }else{
            deactivate();
        }

        return this;
    }
}