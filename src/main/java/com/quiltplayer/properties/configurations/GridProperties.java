package com.quiltplayer.properties.configurations;

import java.io.Serializable;

/**
 * Properties regarding grids.
 * 
 * @author Vlado Palczynski
 * 
 */
public class GridProperties implements Serializable {

    private static final long serialVersionUID = -6389079542376185333L;

    private int quiltGrid = 4;

    private int albumsGrid = 4;

    private int artistGrid = 4;

    /**
     * @return the quiltGrid
     */
    public final int getQuiltGrid() {
        return quiltGrid;
    }

    /**
     * @param quiltGrid
     *            the quiltGrid to set
     */
    public final void setQuiltGrid(int quiltGrid) {
        if (quiltGrid > 0)
            this.quiltGrid = quiltGrid;
    }

    /**
     * @return the albumsGrid
     */
    public final int getAlbumsGrid() {
        return albumsGrid;
    }

    /**
     * @param albumsGrid
     *            the albumsGrid to set
     */
    public final void setAlbumsGrid(int albumsGrid) {
        if (albumsGrid > 0)
            this.albumsGrid = albumsGrid;
    }

    /**
     * @return the artistGrid
     */
    public final int getArtistGrid() {
        return artistGrid;
    }

    /**
     * @param artistGrid
     *            the artistGrid to set
     */
    public final void setArtistGrid(int artistGrid) {
        this.artistGrid = artistGrid;
    }
}
