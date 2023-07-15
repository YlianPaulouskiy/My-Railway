package by.itacademy.railway.configuration;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresSqlExtension implements BeforeAllCallback {

    private PostgreSQLContainer<?> container;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        container = new PostgreSQLContainer<>("postgres:15");
        container.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
    }

}
