package br.com.ph3nr1qu3.application;


import br.com.ph3nr1qu3.core.category.Category;

public class UseCase {

    public Category execute(){
        return Category.newCategory();
    }

}