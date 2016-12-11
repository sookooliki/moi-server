package ru.itmo.ipm.config;

import org.apache.jena.jdbc.remote.RemoteEndpointDriver;
import org.apache.jena.jdbc.remote.connections.RemoteEndpointConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by alexander on 10.12.16.
 */
@Configuration
public class DataConfig {

    @Bean
    Connection connection() throws SQLException {
        RemoteEndpointDriver.register();
        return DriverManager.getConnection("jdbc:jena:remote:query=http://dbpedia.org/sparql");
    }
}
