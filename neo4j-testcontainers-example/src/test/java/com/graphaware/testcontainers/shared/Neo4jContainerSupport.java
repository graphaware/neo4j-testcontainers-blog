package com.graphaware.testcontainers.shared;

import org.testcontainers.containers.GenericContainer;

/**
 *
 */
public class Neo4jContainerSupport {

    private static GenericContainer neo4j = new GenericContainer("neo4j:3.5.0")
            .withEnv("NEO4J_AUTH", "neo4j/Password123")
            .withExposedPorts(7687);


    public static void start() {
        if (!neo4j.isRunning()) {
            neo4j.start();
        }

    }

    public static String uri() {
        return "bolt://" + neo4j.getContainerIpAddress() + ":" + neo4j.getMappedPort(7687);
    }
}
