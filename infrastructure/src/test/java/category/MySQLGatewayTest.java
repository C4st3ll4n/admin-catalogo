package category;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;
import java.util.Collection;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@DataJpaTest
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[MySQLGateway]")
})
@ExtendWith(MySQLGatewayTest.CleanupExtension.class)
public @interface MySQLGatewayTest {

    class CleanupExtension implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext extensionContext) {
            final var repos = SpringExtension
                    .getApplicationContext(extensionContext)
                    .getBeansOfType(CrudRepository.class)
                    .values();

            cleanup(repos);
        }

        private void cleanup(final Collection<CrudRepository> repository) {
            repository.forEach(CrudRepository::deleteAll);
        }
    }

}
