package category.delete;

import br.com.ph3nr1qu3.application.category.delete.DefaultDeleteCategoryUseCase;
import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryGateway;
import br.com.ph3nr1qu3.core.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTests {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @BeforeEach
    void cleanup(){
        Mockito.reset(gateway);
    }

    @Test
    void givenAValidId_WhenCallsDeleteCategory_ShouldBeOK(){
        final var aCategory = Category.newCategory("Filmes", "Mais assistidos", true);
        final var expectedId = aCategory.getId();

        doNothing().when(gateway).deleteById(expectedId.getValue());

        Assertions.assertDoesNotThrow(()-> useCase.execute(expectedId.getValue()));

        Mockito.verify(gateway, times(1)).deleteById(expectedId.getValue());
    }

    @Test
    void givenAInvalidId_WhenCallsDeleteCategory_ShouldBeOK(){
        final var expectedId = CategoryID.from("123");

        doNothing().when(gateway).deleteById(expectedId.getValue());

        Assertions.assertDoesNotThrow(()-> useCase.execute(expectedId.getValue()));

        Mockito.verify(gateway, times(1)).deleteById(expectedId.getValue());
    }

    @Test
    void givenAValidId_WhenGatewayTrowsError_ShouldReturnException(){

        final var aCategory = Category.newCategory("Filmes", "Mais assistidos", true);
        final var expectedId = aCategory.getId();

        doThrow(new IllegalStateException("Gateway error")).when(gateway).deleteById(expectedId.getValue());

        Assertions.assertThrows(IllegalStateException.class,()-> useCase.execute(expectedId.getValue()));

        Mockito.verify(gateway, times(1)).deleteById(expectedId.getValue());

    }
}
