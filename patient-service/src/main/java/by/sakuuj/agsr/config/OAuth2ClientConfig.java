package by.sakuuj.agsr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.endpoint.DefaultPasswordTokenResponseClient;

@Configuration(proxyBeanMethods = false)
public class OAuth2ClientConfig {

    @Bean
    @SuppressWarnings("deprecation")
    DefaultPasswordTokenResponseClient resourceOwnerPasswordCredentialsClient() {

        return new DefaultPasswordTokenResponseClient();
    }
}
