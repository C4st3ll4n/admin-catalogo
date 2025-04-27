package category.retrieve.list;

import br.com.ph3nr1qu3.application.category.retrieve.list.CategortListOutput;
import br.com.ph3nr1qu3.application.category.retrieve.list.DefaultListCategoriesUseCase;
import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.core.category.CategoryGateway;
import br.com.ph3nr1qu3.core.category.CategorySearchQuery;
import br.com.ph3nr1qu3.core.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTests {

    @InjectMocks
    DefaultListCategoriesUseCase usecase;

    @Mock
    CategoryGateway gateway;

    @BeforeEach
    void cleanup() {
        Mockito.reset(gateway);
    }

    @Test
    void givenAValidQuery_WhenCallsListCategories_ShouldReturnCategories() {
        var page = 0;
        var perPage = 10;
        var terms = "";
        var sort = "createdAt";
        var direction = "asc";

        var query = new CategorySearchQuery(page, perPage, terms, sort, direction);

        final var cats = List.of(
                Category.newCategory("Filmes", "Filmes ué", true),
                Category.newCategory("Series", "Nope", true)
        );

        final var pagination = new Pagination<>(page, perPage, cats.size(), cats);

        final var expectedResult = pagination.map(CategortListOutput::from);

        Mockito.when(gateway.findAll(query)).thenReturn(pagination);

        final var result = usecase.execute(query);


        Assertions.assertEquals(result.items().size(), 2);
        Assertions.assertEquals(expectedResult, result);

    }

    @Test
    void givenAValidQuery_WhenEmptyListCategories_ShouldReturnEmpty() {
        var page = 0;
        var perPage = 10;
        var terms = "";
        var sort = "createdAt";
        var direction = "asc";

        var query = new CategorySearchQuery(page, perPage, terms, sort, direction);

        final List<Category> cats = List.of();

        final var pagination = new Pagination<>(page, perPage, cats.size(), cats);

        final var expectedResult = pagination.map(CategortListOutput::from);

        Mockito.when(gateway.findAll(query)).thenReturn(pagination);

        final var result = usecase.execute(query);


        Assertions.assertEquals(result.items().size(), 0);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void givenAValidQuery_WhenGatewayThrows_ShouldReturnException() {
        final var query = new CategorySearchQuery(0,10,"any", "name", "asc");
        Mockito.doThrow(new IllegalStateException()).when(gateway).findAll(Mockito.any(CategorySearchQuery.class));
        Assertions.assertThrows(IllegalStateException.class,()-> usecase.execute(query));

        Mockito.verify(gateway, times(1)).findAll(query);

    }


}
