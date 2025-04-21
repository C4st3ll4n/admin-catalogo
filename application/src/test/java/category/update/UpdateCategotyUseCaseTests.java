package category.update;

import br.com.ph3nr1qu3.application.category.update.DefaultUpdateCategoryUseCase;
import br.com.ph3nr1qu3.application.category.update.UpdateCategoryCommand;
import br.com.ph3nr1qu3.application.category.update.UpdateCategoryOutput;
import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryGateway;
import br.com.ph3nr1qu3.core.category.CategoryID;
import br.com.ph3nr1qu3.core.exception.DomainException;
import br.com.ph3nr1qu3.core.validation.handler.Notification;
import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategotyUseCaseTests {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    void cleanup(){
        Mockito.reset(gateway);
    }


    @Test
    void givenAValidCategoryShouldUpdateSuccessfullyAndReturnCategoryId() {
        final var aCategory = Category.newCategory("Filme", "", true);

        final var expectedName = "Filmes";
        final var expectedDescription = "Categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        when(gateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(gateway, times(1)).findById(eq(expectedId)); // ← CategoryID aqui

        verify(gateway, times(1)).update(argThat(updatedCategory ->
                updatedCategory.getName().equals(expectedName) &&
                        updatedCategory.getDescription().equals(expectedDescription) &&
                        updatedCategory.isActive() == expectedIsActive &&
                        updatedCategory.getId().equals(expectedId)
        ));
    }


    @Test
    void testUpdateWithInvalidName_shouldReturnNotificationWithError() {
        final var category = Category.newCategory("Filmes", "description", true);
        final var expectedId = category.getId();
        final String invalidName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' cannot be null";

        when(gateway.findById(expectedId)).thenReturn(Optional.of(category));

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                invalidName,
                "description",
                true
        );

        final Either<Notification, UpdateCategoryOutput> result = useCase.execute(command);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorCount, result.getLeft().getErrors().size());
        assertEquals(expectedErrorMessage, result.getLeft().getErrors().get(0).message());
        verify(gateway, never()).update(any());
    }

    @Test
    void testUpdateWithNonExistentId_shouldThrowDomainException() {
        final var expectedId = CategoryID.from("invalid_id");
        final var expectedErrorMessage = "Category with id invalid_id was not found";

        when(gateway.findById(expectedId)).thenReturn(Optional.empty());

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                "name",
                "description",
                true
        );

        final var exception = assertThrows(
                DomainException.class,
                () -> useCase.execute(command)
        );

        assertEquals(expectedErrorMessage, exception.getErrors().get(0).message());
    }

    @Test
    void testUpdateWhenGatewayThrowsRandomError_shouldReturnNotification() {
        final var category = Category.newCategory("Filmes", "description", true);
        final var expectedId = category.getId();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway error";

        when(gateway.findById(expectedId)).thenReturn(Optional.of(category));
        when(gateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                "name",
                "description",
                true
        );

        final Either<Notification, UpdateCategoryOutput> result = useCase.execute(command);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorCount, result.getLeft().getErrors().size());
        assertEquals(expectedErrorMessage, result.getLeft().getErrors().get(0).message());
    }

    @Test
    void testUpdateWithMultipleValidationErrors_shouldReturnNotificationWithAllErrors() {
        final var category = Category.newCategory("Filmes", "description", true);
        final var expectedId = category.getId();
        final var expectedErrorCount = 2;

        when(gateway.findById(expectedId)).thenReturn(Optional.of(category));

        final var command = UpdateCategoryCommand.with(
                expectedId.getValue(),
                null,
                null,
                true
        );

        final Either<Notification, UpdateCategoryOutput> result = useCase.execute(command);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorCount, result.getLeft().getErrors().size());
        verify(gateway, never()).update(any());
    }
}
