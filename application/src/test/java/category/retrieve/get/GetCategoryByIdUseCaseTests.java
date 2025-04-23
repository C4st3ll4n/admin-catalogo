package category.retrieve.get;

import br.com.ph3nr1qu3.application.category.retrieve.get.CategoryOutput;
import br.com.ph3nr1qu3.application.category.retrieve.get.DefaultGetCategoryByIdUseCase;
import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryGateway;
import br.com.ph3nr1qu3.core.category.CategoryID;
import br.com.ph3nr1qu3.core.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTests {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    void cleanup() {
        Mockito.reset(gateway);
    }

    @Test
    void givenAValidId_WhenCallGetCategory_ShouldReturnACategory() {
        var cat = Category.newCategory("Filmes", "Filmes mais assistidos", true);
        var id = cat.getId();

        when(gateway.findById(any(CategoryID.class))).thenReturn(Optional.of(cat));

        final var actCat = useCase.execute(id.getValue());

        Assertions.assertEquals(CategoryOutput.from(cat), actCat);

    }

    @Test
    void givenAInvalidId_WhenCallGetCategory_ShouldReturnNotFound() {
        final var id = CategoryID.from("123");
        final var expectedError = "Category with id %s was not found".formatted(id.getValue());
        when(gateway.findById(any(CategoryID.class))).thenReturn(Optional.empty());

        final var actExc = Assertions.assertThrows(DomainException.class, () -> useCase.execute(id.getValue()));

        Assertions.assertEquals(expectedError, actExc.getMessage());
    }

    @Test
    void givenAValidId_WhenGatewayThrows_ShouldReturnException() {
        var cat = Category.newCategory("Filmes", "Filmes mais assistidos", true);
        var id = cat.getId();

        final var expectedError = "Gateway Error";
        when(gateway.findById(any(CategoryID.class))).thenThrow(new IllegalStateException("Gateway Error"));

        final var actExc = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(id.getValue()));

        Assertions.assertEquals(expectedError, actExc.getMessage());
    }
}
