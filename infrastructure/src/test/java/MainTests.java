import br.com.ph3nr1qu3.infrastructure.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTests {

    @Test
    void testMain() {
        Assertions.assertNotNull(new Main());
        Main.main(new String[]{});
    }
}
