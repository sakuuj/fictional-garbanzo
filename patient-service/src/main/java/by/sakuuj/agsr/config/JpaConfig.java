package by.sakuuj.agsr.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories("by.sakuuj.agsr.repository")
public class JpaConfig {
}
