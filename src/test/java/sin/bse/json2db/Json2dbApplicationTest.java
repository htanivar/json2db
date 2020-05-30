package sin.bse.json2db;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.Assert.*;

@SpringBootTest
@Profile("unittest")
public class Json2dbApplicationTest {

    @Test
    public void main() {
        Json2dbApplication.main(new String[] {});
    }
}