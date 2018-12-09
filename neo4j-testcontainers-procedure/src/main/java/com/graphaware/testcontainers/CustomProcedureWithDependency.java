package com.graphaware.testcontainers;

import java.util.List;
import java.util.stream.Stream;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.logging.Log;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.UserFunction;

import static com.google.common.collect.Lists.newArrayList;

/**
 */
public class CustomProcedureWithDependency {

    @UserFunction(name = "example.testWithDependency")
    public List<Long> testWithDependency() {
        return newArrayList(1L, 42L);
    }
}
