package br.com.ph3nr1qu3.core.category;

import br.com.ph3nr1qu3.core.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category category);

    void deleteById(String categoryId);

    Optional<Category> findById(String categoryId);

    Category update(Category category);

    Pagination<Category> findAll(CategorySearchQuery query);
}
