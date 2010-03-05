package com.quiltplayer.model.neo;

import org.neo4j.graphdb.Transaction;

import com.quiltplayer.properties.Config;

/**
 * Wrapper Neo transaction class.
 * 
 * @author Vlado Palczynski
 * 
 */
public class NeoTx {
    protected static Transaction beginTx() {
        return Config.getNeoDb().beginTx();
    }

    protected static void finishTx(final Transaction tx) {
        tx.success();
        tx.finish();
    }
}
