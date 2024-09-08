import br.com.ph3nr1qu3.core.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTests {
    @Test
    void testCreate(){
        Assertions.assertNotNull(new Category());
    }
}
