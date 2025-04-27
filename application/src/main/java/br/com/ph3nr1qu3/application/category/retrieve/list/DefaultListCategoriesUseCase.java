package br.com.ph3nr1qu3.application.category.retrieve.list;

import br.com.ph3nr1qu3.core.category.CategoryGateway;
import br.com.ph3nr1qu3.core.category.CategorySearchQuery;
import br.com.ph3nr1qu3.core.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway gateway;

    public DefaultListCategoriesUseCase(CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public Pagination<CategortListOutput> execute(CategorySearchQuery param) {
        return gateway.findAll(param).map(CategortListOutput::from);
    }
}
