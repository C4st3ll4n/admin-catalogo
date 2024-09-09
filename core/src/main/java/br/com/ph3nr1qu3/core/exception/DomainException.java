package br.com.ph3nr1qu3.core.exception;

import br.com.ph3nr1qu3.core.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {
    private final List<Error> errors;

    public DomainException(final String message, List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException("", errors);
    }


    public static DomainException with(final Error error) {
        return new DomainException(error.message(), List.of(error));
    }


    public List<Error> getErrors() {
        return errors;
    }
}
