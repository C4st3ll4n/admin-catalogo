package br.com.ph3nr1qu3.core.validation.handler;

import br.com.ph3nr1qu3.core.exception.DomainException;
import br.com.ph3nr1qu3.core.validation.Error;
import br.com.ph3nr1qu3.core.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Error aError) {
        return new Notification(List.of(aError));
    }

    public static Notification create(Throwable throwable) {
        return create(new Error(throwable.getMessage()));
    }

    @Override
    public ValidationHandler append(Error error) {
        this.errors.add(error);
        return this;
    }

    @Override
    public ValidationHandler append(ValidationHandler handler) {
        this.errors.addAll(handler.getErrors());
        return this;
    }

    @Override
    public ValidationHandler validate(Validation validation) {
        try {
            validation.validate();
        } catch (DomainException de) {
            this.errors.addAll(de.getErrors());
        } catch (Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }

}
