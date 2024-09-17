package br.com.ph3nr1qu3.application.category.create;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {
    public static CreateCategoryOutput from(
            final Category aCategory
    ) {
        return new CreateCategoryOutput(aCategory.getId());
    }
}
