package sin.bse.json2db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Json2dbApplicationTests {

    @Test
    public void contextLoads() {
        Json2dbApplication.main(new String[]{});
    }

}
