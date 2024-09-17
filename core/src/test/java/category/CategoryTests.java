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
    void givenANullNameWhenCallCategoryConstructorThenThrowError() {
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

    @Test
    void givenAEmptyNameWhenCallCategoryConstructorThenThrowError() {
        final var expectedErrorMessage = "'name' cannot be empty";
        final var expectedErrorCount = 1;

        final String expectedName = "";
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualError = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }

    @Test
    void givenAInvalidNameLengthLT3WhenCallCategoryConstructorThenThrowError() {
        final var expectedErrorMessage = "'name' should be grater then 3 letters";
        final var expectedErrorCount = 1;

        final String expectedName = "An ";
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualError = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }

    @Test
    void givenAInvalidNameLengthGT255WhenCallCategoryConstructorThenThrowError() {
        final var expectedErrorMessage = "'name' should be smaller then 255 letters";

        final var expectedName = """
                Gostaria de enfatizar que o consenso sobre a necessidade de qualificação auxilia a preparação e a
                composição das posturas dos órgãos dirigentes com relação às suas atribuições.
                Do mesmo modo, a estrutura atual da organização apresenta tendências no sentido de aprovar a
                manutenção das novas proposições.
                """;
        final var expectedErrorCount = 1;
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualError = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualError.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());

    }

    @Test
    void givenAValidEmprtDescriptionWhenCallCategoryConstructorThenInstantiateClass() {

        final var expectedName = "Any Category";
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

    }

    @Test
    void givenAIsActiveFalseWhenCallCategoryConstructorThenInstantiateClass() {

        final var expectedName = "Any Category";
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = false;
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);


        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());

        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenAValidActiveCategoryWhenCallDeactivateThenReturnCategoryInactivated() {

        final var expectedName = "Any Category";
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = true;
        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();
        Assertions.assertTrue(aCategory.isActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.deactivate();
        Assertions.assertEquals(actualCategory.getId(), aCategory.getId());

        Assertions.assertEquals(aCategory.getName(), actualCategory.getName());
        Assertions.assertEquals(aCategory.getDescription(), actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
    }

    @Test
    void givenAValidInactiveCategoryWhenCallActivateThenReturnCategoryActivated() {

        final var expectedName = "Any Category";
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = false;
        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();
        Assertions.assertFalse(aCategory.isActive());
        Assertions.assertNotNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.activate();
        Assertions.assertEquals(actualCategory.getId(), aCategory.getId());

        Assertions.assertEquals(aCategory.getName(), actualCategory.getName());
        Assertions.assertEquals(aCategory.getDescription(), actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenAValidCategoryWhenCallUpdateThenReturnCategoryUpdated() {

        final var expectedName = "Any Category";
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = true;
        final var actualCategory = Category.newCategory("Cat", "Des", expectedIsActive);

        final var updatedAt = actualCategory.getUpdatedAt();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory.getId());

        var updatedCategory = actualCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(updatedCategory.getName(), expectedName);
        Assertions.assertEquals(updatedCategory.getDescription(), expectedDescription);
        Assertions.assertTrue(updatedCategory.isActive());
        Assertions.assertNotNull(updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(updatedCategory.getDeletedAt());
    }

    @Test
    void givenAValidCategoryWhenCallUpdateToInactiveThenReturnCategoryUpdated() {

        final var expectedName = "Any Category";
        final var expectedDescription = "Categoria Mais Assistida";
        final var expectedIsActive = false;
        final var actualCategory = Category.newCategory("Cat", "Des", true);

        final var updatedAt = actualCategory.getUpdatedAt();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory.getId());

        var updatedCategory = actualCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(updatedCategory.getName(), expectedName);
        Assertions.assertEquals(updatedCategory.getDescription(), expectedDescription);
        Assertions.assertFalse(updatedCategory.isActive());
        Assertions.assertNotNull(updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(updatedCategory.getDeletedAt());
    }
}
