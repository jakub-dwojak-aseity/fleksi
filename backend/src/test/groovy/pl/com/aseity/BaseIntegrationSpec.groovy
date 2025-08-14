package pl.com.aseity

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import spock.lang.Specification

@SpringBootTest
@Testcontainers
abstract class BaseIntegrationSpec extends Specification {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("fleksi_test")
            .withUsername("fleksi")
            .withPassword("fleksi")

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
            .withExposedPorts(6379)

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl)
        registry.add("spring.datasource.username", postgres::getUsername)
        registry.add("spring.datasource.password", postgres::getPassword)
        
        registry.add("spring.data.redis.host", redis::getHost)
        registry.add("spring.data.redis.port", redis::getFirstMappedPort)
        
        registry.add("spring.profiles.active", "test")
        registry.add("spring.liquibase.contexts", "dev")
    }
}