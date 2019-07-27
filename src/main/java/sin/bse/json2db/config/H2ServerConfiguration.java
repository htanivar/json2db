package sin.bse.json2db.config;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.sql.SQLException;

@Configuration
@Profile("local")
public class H2ServerConfiguration {

    @Value("${h2.tcp.port:9090}")
    private String h2TcpPort;
    @Value("${h2.web.port:8090}")
    private String h2WebPort;

    @Bean
    @ConditionalOnExpression("${h2.tcp.enabled:true}")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp","-tcpAllowOthers","-tcpPort",h2TcpPort).start();
    }

    @Bean
    @ConditionalOnExpression("${h2.web.enabled:false}")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web","-webAllowOthers","-webPort",h2WebPort).start();
    }


}
