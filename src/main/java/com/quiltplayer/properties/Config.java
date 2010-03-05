package com.quiltplayer.properties;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.util.GraphDatabaseUtil;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Config {

    private static final String NEO_FOLDER = "/neo";

    private static GraphDatabaseService neoDb;

    private static GraphDatabaseUtil neoUtil;

    @Bean
    public GraphDatabaseService neoDb() {
        final String neoFolder = Configuration.getInstance().getRoot() + NEO_FOLDER;
        neoDb = new EmbeddedGraphDatabase(neoFolder);

        neoUtil = new GraphDatabaseUtil(neoDb);

        return neoDb;
    }

    public static GraphDatabaseService getNeoDb() {
        return neoDb;
    }

    public static GraphDatabaseUtil getNeoUtil() {
        return neoUtil;
    }
}