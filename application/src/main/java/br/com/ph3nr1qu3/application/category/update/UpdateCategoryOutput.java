package br.com.ph3nr1qu3.application.category.update;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id,
        String description,
        boolean isActive,
        String name
) {

    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId(), aCategory.getDescription(), aCategory.isActive(), aCategory.getName());
    }
}
