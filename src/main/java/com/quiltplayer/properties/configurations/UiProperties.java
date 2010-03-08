package com.quiltplayer.properties.configurations;

import java.io.Serializable;

public class UiProperties implements Serializable {

    private static final long serialVersionUID = 2907244615708763132L;

    private boolean doubleBuffer = false;

    /**
     * @return the doubleBuffer
     */
    public final boolean isDoubleBuffer() {
        return doubleBuffer;
    }

    /**
     * @param doubleBuffer
     *            the doubleBuffer to set
     */
    public final void setDoubleBuffer(boolean doubleBuffer) {
        this.doubleBuffer = doubleBuffer;
    }
}
