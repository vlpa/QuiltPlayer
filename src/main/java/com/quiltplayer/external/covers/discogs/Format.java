package com.quiltplayer.external.covers.discogs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vlado
 */
public class Format implements Serializable {

    private static final long serialVersionUID = -3405376029410645245L;

    private String name;

    private String qty;

    private List<String> descriptions = new ArrayList<String>();

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

    /**
     * @return the qty
     */
    public String getQty() {
        return qty;
    }

    /**
     * @param qty
     *            the qty to set
     */
    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     * @return the descriptions
     */
    public List<String> getDescriptions() {
        return descriptions;
    }

    /**
     * @param descriptions
     *            the descriptions to set
     */
    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

}
