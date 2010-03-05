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
package com.quiltplayer.model.neo;

import java.io.File;

import org.neo4j.graphdb.Node;

import com.quiltplayer.external.covers.model.LocalImage;
import com.quiltplayer.properties.Config;

/**
 * Neo implementation of LocalImage.
 * 
 * @author Vlado Palczynski
 */
public class NeoLocalImage implements LocalImage {
    /**
     * Property height.
     */
    private static final String PROPERTY_HEIGHT = "height";

    /**
     * Property width.
     */
    private static final String PROPERTY_WIDTH = "width";

    /**
     * Property path large.
     */
    private static final String PROPERTY_PATH_LARGE = "pathLarge";

    /**
     * Property path small.
     */
    private static final String PROPERTY_PATH_SMALL = "pathSmall";

    /**
     * Property path medium.
     */
    private static final String PROPERTY_PATH_MEDIUM = "pathMedium";

    /**
     * Property type.
     */
    private static final String PROPERTY_TYPE = "type";

    /**
     * Property counter.
     */
    private static final String PROPERTY_COUNTER = "counter";

    /**
     * The underlying node.
     */
    private Node node;

    public NeoLocalImage(final Node node) {
        this.node = node;
    }

    /**
     * @return the node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * @return the counter
     */
    public int getCounter() {
        return (Integer) Config.getNeoUtil().getProperty(node, PROPERTY_COUNTER);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#getHeight()
     */
    @Override
    public String getHeight() {
        return (String) Config.getNeoUtil().getProperty(node, PROPERTY_HEIGHT);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#getPath()
     */
    @Override
    public File getLargeImage() {
        return new File((String) Config.getNeoUtil().getProperty(node, PROPERTY_PATH_LARGE));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#getPath150()
     */
    @Override
    public File getSmallImage() {
        return new File((String) Config.getNeoUtil().getProperty(node, PROPERTY_PATH_SMALL));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#getPath250()
     */
    @Override
    public File getMediumImage() {
        return new File((String) Config.getNeoUtil().getProperty(node, PROPERTY_PATH_MEDIUM));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#getType()
     */
    @Override
    public String getType() {
        return (String) Config.getNeoUtil().getProperty(node, PROPERTY_TYPE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#getWidth()
     */
    @Override
    public String getWidth() {
        return (String) Config.getNeoUtil().getProperty(node, PROPERTY_WIDTH);
    }

    /**
     * @param counter
     *            the counter to set
     */
    public void setCounter(final int counter) {
        Config.getNeoUtil().setProperty(node, PROPERTY_COUNTER, counter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#setHeight(java.lang.String)
     */
    @Override
    public void setHeight(final String height) {
        Config.getNeoUtil().setProperty(node, PROPERTY_HEIGHT, height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#setPath(java.lang.String)
     */
    @Override
    public void setLargeImage(final File path) {
        Config.getNeoUtil().setProperty(node, PROPERTY_PATH_LARGE, path.getAbsolutePath());

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#setPath150(java.lang.String)
     */
    @Override
    public void setSmallImage(final File path150) {
        Config.getNeoUtil().setProperty(node, PROPERTY_PATH_SMALL,
                (String) path150.getAbsolutePath());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#setPath250(java.lang.String)
     */
    @Override
    public void setMediumImage(final File path250) {
        Config.getNeoUtil().setProperty(node, PROPERTY_PATH_MEDIUM, path250.getAbsolutePath());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#setType(java.lang.String)
     */
    @Override
    public void setType(final String type) {
        Config.getNeoUtil().setProperty(node, PROPERTY_TYPE, type);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.model.LocalImage#setWidth(java.lang.String)
     */
    @Override
    public void setWidth(final String width) {
        Config.getNeoUtil().setProperty(node, PROPERTY_WIDTH, width);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((node == null) ? 0 : node.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NeoLocalImage other = (NeoLocalImage) obj;
        if (node == null) {
            if (other.node != null)
                return false;
        }
        else if (node.getId() != other.node.getId())
            return false;
        return true;
    }

}
