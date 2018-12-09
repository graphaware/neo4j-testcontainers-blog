package com.graphaware.testcontainers;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class CustomProcedureWithDependencyIT {

    @ClassRule
    public static GenericContainer neo4j = new GenericContainer("neo4j:3.5.0")
            .withEnv("NEO4J_AUTH", "neo4j/Password123")
            .withFileSystemBind("target/neo4j-testcontainers-procedure-all.jar", "/plugins/neo4j-testcontainers-procedure-all.jar", BindMode.READ_ONLY)
            .withExposedPorts(7687);


    @Test
    public void shouldAnswerWithOne() {
        String uri = "bolt://" + neo4j.getContainerIpAddress() + ":" + neo4j.getMappedPort(7687);

        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic("neo4j", "Password123"))) {

            try (Session session = driver.session()) {
                StatementResult result = session.run("RETURN example.testWithDependency() AS value");
                List<Long> value = result.single().get("value").asList(Value::asLong);
                assertThat(value).containsExactly(1L, 42L);
            }

        }
    }
}
