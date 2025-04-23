package br.com.ph3nr1qu3.application.category.update;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryGateway;
import br.com.ph3nr1qu3.core.category.CategoryID;
import br.com.ph3nr1qu3.core.exception.DomainException;
import br.com.ph3nr1qu3.core.validation.Error;
import br.com.ph3nr1qu3.core.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase{

    private final CategoryGateway gateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway){
        this.gateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand param) {
        var id = CategoryID.from(param.id());
        var name = param.name();
        var description = param.description();
        var active = param.isActive();

        Category category = this.gateway.findById(id).orElseThrow(notFound(id));
        final var notification = Notification.create();
        category.update(name, description, active).validate(notification);

        return notification.hasError()? API.Left(notification): update(category);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category category){
        return API.Try(()-> this.gateway.update(category)).toEither().bimap(Notification::create, UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(final CategoryID id){
        return ()-> DomainException.with(new Error("Category with id %s was not found".formatted(id.getValue())));
    }
}
