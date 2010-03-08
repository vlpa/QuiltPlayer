package com.quiltplayer.properties.configurations;

import java.io.Serializable;

public class SpotifyProperties implements Serializable {

    private static final long serialVersionUID = 1786341200751976052L;

    private boolean useSpotify;

    private String spotifyUserName = "";

    private char[] spotifyPassword = new char[0];

    /**
     * @return the useSpotify
     */
    public final boolean isUseSpotify() {
        return useSpotify;
    }

    /**
     * @param useSpotify
     *            the useSpotify to set
     */
    public final void setUseSpotify(boolean useSpotify) {
        this.useSpotify = useSpotify;
    }

    /**
     * @return the spotifyPassword
     */
    public final String getSpotifyPassword() {
        return new String(spotifyPassword);
    }

    /**
     * @param spotifyPassword
     *            the spotifyPassword to set
     */
    public final void setSpotifyPassword(char[] spotifyPassword) {
        this.spotifyPassword = spotifyPassword;
    }

    /**
     * @return the spotifyUserName
     */
    public final String getSpotifyUserName() {
        return spotifyUserName;
    }

    /**
     * @param spotifyUserName
     *            the spotifyUserName to set
     */
    public final void setSpotifyUserName(String spotifyUserName) {
        this.spotifyUserName = spotifyUserName;
    }
}
