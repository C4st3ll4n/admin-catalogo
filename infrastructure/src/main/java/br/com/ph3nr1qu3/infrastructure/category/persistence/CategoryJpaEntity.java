package br.com.ph3nr1qu3.infrastructure.category.persistence;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Table(name = "category")
@Entity
public class CategoryJpaEntity {

    private CategoryJpaEntity(String id, String name, String description, boolean active, Instant createdAt, Instant deletedAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
    }

    public CategoryJpaEntity() {
    }

    public static CategoryJpaEntity from(Category category) {
        return new CategoryJpaEntity(
                category.getId().getValue(),
                category.getName(),
                category.getDescription(),
                category.isActive(),
                category.getCreatedAt(),
                category.getDeletedAt(),
                category.getUpdatedAt()
        );
    }

    public Category toAggregate() {
        return Category.with(
                CategoryID.from(this.id),
                this.getName(),
                this.getDescription(),
                this.isActive(),
                this.getCreatedAt(),
                this.getUpdatedAt(),
                this.getDeletedAt()
        );
    }

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(length = 4000)
    private String description;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    @Column(columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "id -> %s; name -> %s; active -> %s".formatted(id, name, active);
    }
}
