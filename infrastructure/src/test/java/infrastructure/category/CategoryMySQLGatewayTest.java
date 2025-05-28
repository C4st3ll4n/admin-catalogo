package infrastructure.category;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.infrastructure.category.persistence.CategoryJpaEntity;
import br.com.ph3nr1qu3.infrastructure.category.persistence.CategoryMySQLGateway;
import br.com.ph3nr1qu3.infrastructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway gateway;

    @Autowired
    private CategoryRepository repository;

    @Test
    void givenAValidCategoryWhenCallsCreateShouldReturnANewCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria";
        final var expectedActive = true;

        final var cat = Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(0, repository.count());

        final var actualCat = gateway.create(cat);

        Assertions.assertEquals(1, repository.count());

        Assertions.assertEquals(cat.getId(), actualCat.getId());
        Assertions.assertEquals(expectedDescription, actualCat.getDescription());
        Assertions.assertEquals(expectedName, actualCat.getName());
        Assertions.assertEquals(expectedActive, actualCat.isActive());
        Assertions.assertEquals(cat.getCreatedAt(), actualCat.getCreatedAt());

        final var entity = repository.findById(actualCat.getId().getValue()).get();
        Assertions.assertNotNull(entity);
    }


    @Test
    void givenAValidCategory_whenCallsUpdate_shoudReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A Categoria";
        final var expectedActive = true;

        final var cat = Category.newCategory("expectedName", expectedDescription, expectedActive);
        Assertions.assertEquals(0, repository.count());

        repository.saveAndFlush( CategoryJpaEntity.from(cat));

        final var actualCat = gateway.create(cat);

        Assertions.assertEquals(1, repository.count());

        Assertions.assertEquals(cat.getId(), actualCat.getId());
        Assertions.assertEquals(expectedDescription, actualCat.getDescription());
        Assertions.assertEquals(expectedName, actualCat.getName());
        Assertions.assertEquals(expectedActive, actualCat.isActive());
        Assertions.assertEquals(cat.getCreatedAt(), actualCat.getCreatedAt());

        final var entity = repository.findById(actualCat.getId().getValue()).get();
        Assertions.assertNotNull(entity);
    }

}
