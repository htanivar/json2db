package sin.bse.json2db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "sin.bse.json2db")
@EnableJpaRepositories
public class Json2dbApplication {

    public static void main(String[] args) {
        SpringApplication.run(Json2dbApplication.class, args);
    }

}
