/**
 * QuiltPlayer v1.0 Copyright (C) 2008-2009 Vlado Palczynski
 * vlado.palczynski@quiltplayer.com http://www.quiltplayer.com This program is
 * free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 */
package com.quiltplayer.external.covers.model.filesystem;

import java.io.File;

import com.quiltplayer.external.covers.model.LocalImage;

/**
 * @author vlado
 */
public class LocalImageFileSystem implements LocalImage {

    /**
     * The height.
     */
    private String height;

    /**
     * The width.
     */
    private String width;

    /**
     * The large image.
     */
    private File largeImage;

    /**
     * The small image.
     */
    private File smallImage;

    /**
     * The medium image.
     */
    private File mediumImage;

    /**
     * The type.
     */
    private String type;

    /**
     * @return the height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(final String height) {
        this.height = height;
    }

    /**
     * @return the width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(final String width) {
        this.width = width;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.imagefetcher.model.LocalImage#getLargeImage()
     */
    public File getLargeImage() {
        return largeImage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.imagefetcher.model.LocalImage#getMediumImage()
     */
    public File getMediumImage() {
        return mediumImage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.imagefetcher.model.LocalImage#getSmallImage()
     */
    public File getSmallImage() {
        return smallImage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.imagefetcher.model.LocalImage#setLargeImage(java.io.File)
     */
    public void setLargeImage(File largeImage) {
        this.largeImage = largeImage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.imagefetcher.model.LocalImage#setMediumImage(java.io. File)
     */
    public void setMediumImage(File mediumImage) {
        this.mediumImage = mediumImage;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.imagefetcher.model.LocalImage#setSmallImage(java.io.File)
     */
    public void setSmallImage(File smallImage) {
        this.smallImage = smallImage;
    }

}
