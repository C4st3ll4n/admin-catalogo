package br.com.ph3nr1qu3.core;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID>{
    protected AggregateRoot(final ID identifier) {
        super(identifier);
    }

}
