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
package com.quiltplayer.model;

/**
 * Represents a artist name for publishing.
 * 
 * @author Vlado Palczynski
 */
public class ArtistName {
    /**
     * The name.
     */
    private String name;

    /**
     * The the.
     */
    private boolean the;

    /**
     * @param name
     *            the name to set.
     */
    public ArtistName(final String name) {
        setName(name);
    }

    /**
     * @return the name
     */
    public String getName() {
        if (the) {
            return name + ", The";
        }

        return name;
    }

    public String getNameForSearches() {
        if (name.toLowerCase().endsWith(", the")) {
            return "The " + name.substring(0, name.length() - 5);
        }
        else {
            return name;
        }
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        if (name.toLowerCase().startsWith("the ")) {
            name = name.substring(4, name.length());
            setThe(true);
        }

        this.name = name;
    }

    /**
     * @return the the.
     */
    public boolean isThe() {
        return the;
    }

    /**
     * @param the
     *            the the to set
     */
    public void setThe(final boolean the) {
        this.the = the;
    }

    /**
     * toString() returns the name.
     */
    @Override
    public String toString() {
        return name;
    }

}
