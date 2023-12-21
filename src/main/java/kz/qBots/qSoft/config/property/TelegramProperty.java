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
public class TelegramProperty {
    @Value("${bot.name}")
    private String username;
    @Value("${bot.token}")
    private String token;
}
