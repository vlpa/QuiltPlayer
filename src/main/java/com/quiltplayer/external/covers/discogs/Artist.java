package com.quiltplayer.external.covers.discogs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Artist implements Serializable {

    private static final long serialVersionUID = -7354496239543234582L;

    private List<Image> images = new ArrayList<Image>();

    private String name;

    private String realname;

    private String profile;

    private List<String> urls = new ArrayList<String>();

    private List<String> namevariations = new ArrayList<String>();

    private List<String> aliases = new ArrayList<String>();

    private List<String> members = new ArrayList<String>();

    private List<ArtistRelease> releases = new ArrayList<ArtistRelease>();

    private List<String> groups = new ArrayList<String>();

    private String role;

    /**
     * @return the images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * @param images
     *            the images to set
     */
    public void setImages(List<Image> images) {
        this.images = images;
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

    /**
     * @return the urls
     */
    public List<String> getUrls() {
        return urls;
    }

    /**
     * @param urls
     *            the urls to set
     */
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    /**
     * @return the aliases
     */
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * @param aliases
     *            the aliases to set
     */
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    /**
     * @return the members
     */
    public List<String> getMembers() {
        return members;
    }

    /**
     * @param members
     *            the members to set
     */
    public void setMembers(List<String> members) {
        this.members = members;
    }

    /**
     * @return the releases
     */
    public List<ArtistRelease> getReleases() {
        return releases;
    }

    /**
     * @param releases
     *            the releases to set
     */
    public void setReleases(List<ArtistRelease> releases) {
        this.releases = releases;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname
     *            the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * @return the profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @param profile
     *            the profile to set
     */
    public void setProfile(String profile) {
        this.profile = profile;
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
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Artist other = (Artist) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * @return the groups
     */
    public List<String> getGroups() {
        return groups;
    }

    /**
     * @param groups
     *            the groups to set
     */
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    /**
     * @return the namevariations
     */
    public final List<String> getNamevariations() {
        return namevariations;
    }

    /**
     * @param namevariations
     *            the namevariations to set
     */
    public final void setNamevariations(List<String> namevariations) {
        this.namevariations = namevariations;
    }

}
