package br.com.ph3nr1qu3.infrastructure.category.persistence;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryGateway;
import br.com.ph3nr1qu3.core.category.CategoryID;
import br.com.ph3nr1qu3.core.category.CategorySearchQuery;
import br.com.ph3nr1qu3.core.pagination.Pagination;
import br.com.ph3nr1qu3.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static br.com.ph3nr1qu3.infrastructure.utils.SpecificationUtils.like;

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
    public void deleteById(final String categoryId) {
        if (this.repository.existsById(categoryId)) {
            this.repository.deleteById(categoryId);
        }
    }

    @Override
    public Optional<Category> findById(final String categoryId) {
        return this.repository.findById(categoryId).map(CategoryJpaEntity::toAggregate);
    }

    @Override
    public Optional<Category> findById(CategoryID categoryId) {
        return this.findById(categoryId.getValue());
    }

    @Override
    public Category update(final Category category) {
        return this.create(category);
    }

    @Override
    public Pagination<Category> findAll(final CategorySearchQuery query) {
        var page = PageRequest.of(
                query.page(), query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var catSpec = Optional.ofNullable(query.terms())
                .filter(String::isBlank)
                .map(str -> {
                    return SpecificationUtils.<CategoryJpaEntity>like("name", str)
                            .or(like("description", str));
                })
                .orElse(null);

        final var pageResult = this.repository.findAll(Specification.where(catSpec), page);

        return new Pagination<>(
                pageResult.getNumber(), page.getPageSize(), pageResult.getTotalElements(),
                pageResult.map(CategoryJpaEntity::toAggregate).toList()
        );
    }
}
