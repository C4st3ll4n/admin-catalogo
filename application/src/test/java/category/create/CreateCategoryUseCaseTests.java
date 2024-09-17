package category.create;

import br.com.ph3nr1qu3.application.category.create.CreateCategoryCommand;
import br.com.ph3nr1qu3.application.category.create.DefaultCreateCategoryUseCase;
import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTests {

    @InjectMocks
    private DefaultCreateCategoryUseCase defaultCreateCategoryUseCase;

    @Mock
    private CategoryGateway gateway;

    @Test
    void whenValidParamsThenShouldCreateCategory() {
        final var expectedName = "SPFC 2005";
        final var expectedDescription = "Futebol";
        final var expectedIsActive = true;
        final var expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(gateway.create(any())).thenReturn(expectedCategory);

        final var useCase = defaultCreateCategoryUseCase;
        final var createdCategory = useCase.execute(command);

        Assertions.assertNotNull(createdCategory);
        Assertions.assertNotNull(createdCategory.get().id());

        verify(gateway, times(1)).create(argThat(ac -> ac.getName().equals(expectedCategory.getName()) &&
                ac.getDescription().equals(expectedCategory.getDescription()) &&
                ac.isActive().equals(expectedCategory.isActive() &&
                        Objects.nonNull(ac.getCreatedAt()) &&
                        Objects.nonNull(ac.getUpdatedAt()) &&
                        Objects.nonNull(ac.getId()))));
    }

    @Test
    void whenInvalidParamsThenShouldNotCreateCategory() {
        final String expectedName = null;
        final var expectedDescription = "Futebol";
        final var expectedIsActive = true;
        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
        final var expectedErrorMessage = "'name' cannot be null";
        final var expectedErrorCount = 1;

//        when(gateway.create(any())).thenAnswer(returnsFirstArg());
        final var notification = defaultCreateCategoryUseCase.execute(command).getLeft();

        Assertions.assertEquals(expectedErrorMessage, notification.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());

        Mockito.verify(gateway, times(0)).create(any());

    }

    @Test
    void whenValidParamsInactiveStatusThenShouldCreateCategory() {
        final var expectedName = "SPFC 2005";
        final var expectedDescription = "Futebol";
        final var expectedIsActive = false;
        final var expectedCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(gateway.create(any())).thenReturn(expectedCategory);

        final var createdCategory = defaultCreateCategoryUseCase.execute(command);

        Assertions.assertNotNull(createdCategory);
        Assertions.assertNotNull(createdCategory.get().id());

        verify(gateway, times(1)).create(argThat(ac -> ac.getName().equals(expectedCategory.getName()) &&
                ac.getDescription().equals(expectedCategory.getDescription()) &&
                ac.isActive().equals(expectedCategory.isActive() &&
                        Objects.nonNull(ac.getCreatedAt()) &&
                        Objects.nonNull(ac.getUpdatedAt()) &&
                        Objects.nonNull(ac.getDeletedAt()) &&
                        Objects.nonNull(ac.getId()))));
    }

    @Test
    void whenGatewayFailsThenShouldNotCreateCategoryAndThrows() {
        final String expectedName = "SPFC 2005";
        final var expectedDescription = "Futebol";
        final var expectedIsActive = true;
        final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);
        final var expectedErrorMessage = "Gateway error";

        when(gateway.create(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = defaultCreateCategoryUseCase.execute(command);

        Assertions.assertEquals(expectedErrorMessage, notification.getLeft().getErrors().get(0).message());
        Assertions.assertEquals(1, notification.getLeft().getErrors().size());

        Mockito.verify(gateway, times(1)).create(any());

    }

}
