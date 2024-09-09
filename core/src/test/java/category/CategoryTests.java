package category;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.exception.DomainException;
import br.com.ph3nr1qu3.core.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTests {
    @Test
    void givenAValidParamsWhenCallCategoryConstructorThenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenAInvalidParamsWhenCallCategoryConstructorThenThrowError() {
        final var expectedErrorMessage = "'name' cannot be null";
        final var expectedErrorCount = 1;

        final String expectedName = null;
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualError = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }
}
