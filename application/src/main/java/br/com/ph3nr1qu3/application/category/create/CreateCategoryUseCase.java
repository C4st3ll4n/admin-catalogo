package br.com.ph3nr1qu3.application.category.create;

import br.com.ph3nr1qu3.application.UseCase;
import br.com.ph3nr1qu3.core.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
