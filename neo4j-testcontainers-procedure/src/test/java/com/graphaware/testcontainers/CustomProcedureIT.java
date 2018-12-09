package com.graphaware.testcontainers;

import org.junit.ClassRule;
import org.junit.Test;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;

import static java.time.Duration.ofSeconds;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class CustomProcedureIT {

    @ClassRule
    public static GenericContainer neo4j = new GenericContainer("neo4j:3.5.0")
            .withEnv("NEO4J_AUTH", "neo4j/Password123")
            .withFileSystemBind("target/neo4j-testcontainers-procedure.jar", "/plugins/neo4j-testcontainers-procedure.jar", BindMode.READ_ONLY)
            .withExposedPorts(7687);


    @Test
    public void shouldAnswerWithOne() {
        String uri = "bolt://" + neo4j.getContainerIpAddress() + ":" + neo4j.getMappedPort(7687);

        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic("neo4j", "Password123"))) {

            try (Session session = driver.session()) {
                StatementResult result = session.run("RETURN example.test() AS value");
                long value = result.single().get("value").asLong();
                assertThat(value).isEqualTo(42L);
            }

        }
    }
}
