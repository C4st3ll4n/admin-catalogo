package application;

import br.com.ph3nr1qu3.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = WebServerConfig.class)

public class SampleIT {

}
