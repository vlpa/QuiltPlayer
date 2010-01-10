package com.quiltplayer.model.neo;

import org.neo4j.api.core.Transaction;

import com.quiltplayer.core.storage.neo.NeoSingelton;

/**
 * Wrapper Neo transaction class.
 * 
 * @author Vlado Palczynski
 * 
 */
public class NeoTx {
    protected static Transaction beginTx() {
        return NeoSingelton.getInstance().getNeoService().beginTx();
    }

    protected static void finishTx(final Transaction tx) {
        tx.success();
        tx.finish();
    }
}
