import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

public class CleanupExtension implements BeforeEachCallback {

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