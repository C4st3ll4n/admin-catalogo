package infrastructure.category.persistence;

import br.com.ph3nr1qu3.core.category.Category;
import br.com.ph3nr1qu3.infrastructure.category.persistence.CategoryJpaEntity;
import br.com.ph3nr1qu3.infrastructure.category.persistence.CategoryRepository;
import MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Test
    void givenAnInvalidNullName_whenCallSave_shouldReturnError() {
        final var aCat = Category.newCategory("Films", "Watched the most", true);
        final var aEnt = CategoryJpaEntity.from(aCat);
        aEnt.setName(null);

        final var actExc = Assertions.assertThrows(DataIntegrityViolationException.class, () -> repository.save(aEnt));

        final var actCau = Assertions.assertInstanceOf(PropertyValueException.class, actExc.getClass());

        Assertions.assertEquals("name", actCau.getPropertyName());
        Assertions.assertEquals("not-null property", actCau.getMessage());
    }

}
