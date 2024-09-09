package br.com.ph3nr1qu3.core.category;

import br.com.ph3nr1qu3.core.validation.Error;
import br.com.ph3nr1qu3.core.validation.ValidationHandler;
import br.com.ph3nr1qu3.core.validation.Validator;

import java.util.Objects;

public class CategoryValidator extends Validator {

    private final Category category;
    protected CategoryValidator(final Category aCategory, ValidationHandler handler) {
        super(handler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        final String name = category.getName();
        if(Objects.isNull(name)){
            this.validationHandler().append(new Error("'name' cannot be null"));
            return;
        }

        if(name.isBlank()){
            this.validationHandler().append(new Error("'name' cannot be empty"));
            return;
        }
    }
}
