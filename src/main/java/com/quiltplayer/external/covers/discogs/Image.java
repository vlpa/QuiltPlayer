package com.quiltplayer.external.covers.discogs;

import java.io.Serializable;

public class Image implements Serializable {

    public static final String TYPE_PRIMARY = "primary";

    public static final String TYPE_SECONDARY = "secondary";

    private static final long serialVersionUID = 2323671088154824241L;

    private String height = "";

    private String type = "";

    private String uri = "";

    private String uri150 = "";

    private String uri250 = "";

    private String width = "";

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
    public void setHeight(String height) {
        this.height = height;
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
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the uri150
     */
    public String getUri150() {
        return uri150;
    }

    /**
     * @param uri150
     *            the uri150 to set
     */
    public void setUri150(String uri150) {
        this.uri150 = uri150;
    }

    /**
     * @return the uri250
     */
    public String getUri250() {
        return uri250;
    }

    /**
     * @param uri250
     *            the uri250 to set
     */
    public void setUri250(String uri250) {
        this.uri250 = uri250;
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
    public void setWidth(String width) {
        this.width = width;
    }

}
