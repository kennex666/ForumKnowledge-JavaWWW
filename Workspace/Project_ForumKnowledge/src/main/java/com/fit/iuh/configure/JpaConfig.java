package com.fit.iuh.configure;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // Bật tính năng Auditing trong Spring Data JPA
public class JpaConfig {
}
