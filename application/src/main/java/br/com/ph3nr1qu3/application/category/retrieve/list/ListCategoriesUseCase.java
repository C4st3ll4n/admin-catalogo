package br.com.ph3nr1qu3.application.category.retrieve.list;

import br.com.ph3nr1qu3.application.UseCase;
import br.com.ph3nr1qu3.core.category.CategorySearchQuery;
import br.com.ph3nr1qu3.core.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategortListOutput>> {
}
