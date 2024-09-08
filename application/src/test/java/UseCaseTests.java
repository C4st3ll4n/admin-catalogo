import br.com.ph3nr1qu3.application.UseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCaseTests {

    @Test
    void testCreateUseCase(){
        Assertions.assertNotNull(new UseCase());
        Assertions.assertNotNull(new UseCase().execute());
    }
}
