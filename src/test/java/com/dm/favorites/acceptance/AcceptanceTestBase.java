package com.dm.favorites.acceptance;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(initializers = {AcceptanceTestBase.Initializer.class})
public class AcceptanceTestBase {

    @Container
    public static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:6.2.4-alpine"))
            .withExposedPorts(6379);

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            redis.start();

            configurableApplicationContext.addApplicationListener(applicationEvent -> {
                if (applicationEvent instanceof ContextClosedEvent) {
                    redis.stop();
                }
            });
            TestPropertyValues
                    .of(Map.of("spring.redis.host", redis.getHost(),
                            "spring.redis.port", redis.getFirstMappedPort() + ""))
                    .applyTo(configurableApplicationContext);
        }
    }


}
