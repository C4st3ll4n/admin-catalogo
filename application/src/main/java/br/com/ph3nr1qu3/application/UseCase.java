package br.com.ph3nr1qu3.application;


import br.com.ph3nr1qu3.core.category.Category;

public abstract class UseCase<I,O> {

    public abstract O execute(I param);

}