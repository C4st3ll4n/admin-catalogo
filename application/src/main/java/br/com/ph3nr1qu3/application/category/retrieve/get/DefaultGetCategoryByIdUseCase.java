package br.com.ph3nr1qu3.application.category.retrieve.get;

import br.com.ph3nr1qu3.core.category.CategoryGateway;
import br.com.ph3nr1qu3.core.category.CategoryID;
import br.com.ph3nr1qu3.core.exception.DomainException;
import br.com.ph3nr1qu3.core.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway gateway;

    public DefaultGetCategoryByIdUseCase(CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public CategoryOutput execute(final String param) {
        final var id = CategoryID.from(param);
        return this.gateway.findById(id).map(CategoryOutput::from).orElseThrow(notFound(param));
    }

    private Supplier<DomainException> notFound(String param) {
        return () -> DomainException.with(
                new Error("Category with id %s was not found".formatted(param))
        );
    }
}
