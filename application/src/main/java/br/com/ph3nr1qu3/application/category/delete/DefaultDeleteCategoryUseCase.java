package br.com.ph3nr1qu3.application.category.delete;

import br.com.ph3nr1qu3.core.category.CategoryGateway;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase{

    private final CategoryGateway gateway;

    public DefaultDeleteCategoryUseCase(CategoryGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public void execute(String in) {
        this.gateway.deleteById(in);
    }
}
