package kz.qBots.qSoft.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("application.properties")
public class ServerProperty {
    @Value("${application.host}")
    private String host;
    @Value("${application.port}")
    private String port;
    public String getUrl(){
        return "https://"
                + this.getHost()
                + ":"
                + this.getPort();
    }
}
