package br.com.ph3nr1qu3.core.exception;

public class NoStacktraceException extends RuntimeException{

    public NoStacktraceException(final String message){
        super(message, null);
    }

    public NoStacktraceException(final String message, Throwable cause){
        super(message, cause, true, false);
    }
}
