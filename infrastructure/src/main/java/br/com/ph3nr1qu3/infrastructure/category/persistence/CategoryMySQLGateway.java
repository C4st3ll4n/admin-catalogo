package br.com.ph3nr1qu3.infrastructure.category.persistence;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryGateway;
import br.com.ph3nr1qu3.core.category.CategoryID;
import br.com.ph3nr1qu3.core.category.CategorySearchQuery;
import br.com.ph3nr1qu3.core.pagination.Pagination;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {

    private final CategoryRepository repository;

    public CategoryMySQLGateway(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category create(Category category) {
        return this.repository.save(CategoryJpaEntity.from(category)).toAggregate();
    }

    @Override
    public void deleteById(String categoryId) {

    }

    @Override
    public Optional<Category> findById(String categoryId) {
        return Optional.empty();
    }

    @Override
    public Optional<Category> findById(CategoryID categoryId) {
        return Optional.empty();
    }

    @Override
    public Category update(Category category) {
        return null;
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery query) {
        return null;
    }
}
