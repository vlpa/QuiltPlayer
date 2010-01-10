package com.quiltplayer.external.covers.discogs;

import java.io.Serializable;

/**
 * @author vlado
 */
public class Label implements Serializable {

    private static final long serialVersionUID = 4580450413003833922L;

    private String catno;

    private String name;

    /**
     * @return the catno
     */
    public String getCatno() {
        return catno;
    }

    /**
     * @param catno
     *            the catno to set
     */
    public void setCatno(String catno) {
        this.catno = catno;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
