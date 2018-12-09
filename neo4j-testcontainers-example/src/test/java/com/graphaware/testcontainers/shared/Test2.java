package com.graphaware.testcontainers.shared;

import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class Test2 {

    @BeforeClass
    public static void setUpClass() {
        Neo4jContainerSupport.start();
    }

    @Test
    public void shouldReturnTwo() {
        String uri = Neo4jContainerSupport.uri();
        try (Driver driver = GraphDatabase.driver(uri, AuthTokens.basic("neo4j", "Password123"))) {

            try (Session session = driver.session()) {
                StatementResult result = session.run("RETURN 2 AS value");
                int value = result.single().get("value").asInt();
                assertThat(value).isEqualTo(2);
            }

        }
    }

}
