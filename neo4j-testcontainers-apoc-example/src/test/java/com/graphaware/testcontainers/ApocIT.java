package com.graphaware.testcontainers;

import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class ApocIT {


    @ClassRule
    public static GenericContainer neo4j = new GenericContainer("neo4j:3.5.0")
            .withEnv("NEO4J_AUTH", "neo4j/Password123")
            .withFileSystemBind("target/plugins-apoc-for-test", "/plugins", BindMode.READ_ONLY)
            .withExposedPorts(7687);

    @Test
    public void shouldAnswerWithTrue() throws Exception {
        String uri = "bolt://" + neo4j.getContainerIpAddress() + ":" + neo4j.getMappedPort(7687);
        Driver driver = GraphDatabase.driver(uri, AuthTokens.basic("neo4j", "Password123"));

        try (Session session = driver.session()) {
            StatementResult result = session.run("" +
                    "WITH  [[1, 2], [3, 4]] AS coll\n" +
                    "RETURN apoc.coll.flatten(coll) AS list");

            List<Object> list = result.single().get("list").asList();
            assertThat(list).containsExactly(1L, 2L, 3L, 4L);
        }
    }
}
