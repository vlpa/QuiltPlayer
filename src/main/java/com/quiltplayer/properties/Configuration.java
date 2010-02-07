package com.quiltplayer.properties;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.quiltplayer.view.swing.ColorConstants;
import com.quiltplayer.view.swing.ColorConstantsDark;

/**
 * Storage of needed user properties.
 * 
 * @author Vlado Palczynski
 */
public final class Configuration implements Serializable {

    private static final long serialVersionUID = 7625101231832775970L;

    private static final Logger log = Logger.getLogger(Configuration.class);

    private static final String configurationName = "configuration.ser";

    private static Configuration instance;

    private static String userHome = System.getProperty("user.home");

    private static String rootFolder = "/.quiltplayer";

    private static String storageFolder = "/storage";

    private static String coversFolder = "/covers";

    private String musicPath = null;

    private String apiKey = null;

    private boolean useSpotify;

    private String spotifyUserName = "";

    private char[] spotifyPassword = new char[0];

    private boolean useProxy;

    private int proxyPort;

    private String proxyUrl;

    private String proxyUsername;

    private transient String proxyPassword;

    private static File root = new File(userHome, rootFolder);

    private static File storage = new File(root, storageFolder);

    private static File covers = new File(root, coversFolder);

    private ColorConstants colorConstants = new ColorConstantsDark();

    private boolean fullScreen;

    private Dimension savedDimensionOnFrame;

    private float fontBalancer = 0;

    public static final String lineBreak = System.getProperty("line.separator");

    static {
        root = new File(userHome, rootFolder);
        if (!root.exists()) {
            boolean b = root.mkdir();
            if (!b)
                log.error("Couldn't create root directory.");
        }

        if (!storage.exists()) {
            boolean b = storage.mkdir();
            if (!b)
                log.error("Couldn't create storage directory.");
        }

        if (!covers.exists()) {
            boolean b = covers.mkdir();
            if (!b)
                log.error("Couldn't create covers directory.");
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = retrieveConfiguration();
        }

        return instance;
    }

    public static void setInstance(Configuration config) {
        instance = config;
    }

    /**
     * Location for the root covers.
     */
    public static final String ROOT_PATH = root.getAbsolutePath();

    /**
     * Location for the downloaded album covers.
     */
    public static final String ALBUM_COVERS_PATH = covers.getAbsolutePath();

    /**
     * Location for the storage repository.
     */
    public static final String STORAGE_PATH = storage.getAbsolutePath();

    /**
     * API key for access to Discogs webservice.
     */
    public static final String DISCOGS_API_KEY = "6dfeb90be3";

    /**
     * @return the musicPath
     */
    public String getMusicPath() {
        return musicPath;
    }

    /**
     * @param musicPath
     *            the musicPath to set
     */
    public void setMusicPath(String musicPath) {
        this.musicPath = StringUtils.trimToNull(musicPath);
    }

    /**
     * @return the apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey
     *            the apiKey to set
     */
    public void setApiKey(String apiKey) {
        this.apiKey = StringUtils.trimToNull(apiKey);
    }

    /**
     * @return the proxyPort
     */
    public int getProxyPort() {
        return proxyPort;
    }

    /**
     * @param proxyPort
     *            the proxyPort to set
     */
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    /**
     * @return the proxyUrl
     */
    public String getProxyUrl() {
        return proxyUrl;
    }

    /**
     * @param proxyUrl
     *            the proxyUrl to set
     */
    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }

    /**
     * @return the proxyUsername
     */
    public String getProxyUsername() {
        return proxyUsername;
    }

    /**
     * @param proxyUsername
     *            the proxyUsername to set
     */
    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    /**
     * @return the proxyPassword
     */
    public String getProxyPassword() {
        return proxyPassword;
    }

    /**
     * @param proxyPassword
     *            the proxyPassword to set
     */
    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    /**
     * @return the useProxy
     */
    public boolean isUseProxy() {
        return useProxy;
    }

    /**
     * @param useProxy
     *            the useProxy to set
     */
    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    /**
     * @return the root
     */
    public static File getRoot() {
        return root;
    }

    /*
     * @see org.quiltplayer.core.storage.Storage#storeConfiguration(org.quiltplayer
     * .ConfigurationProperties)
     */
    public synchronized void storeConfiguration() {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            File f = new File(STORAGE_PATH + System.getProperty("file.separator")
                    + configurationName);
            fos = new FileOutputStream(f);
            out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Configuration retrieveConfiguration() {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            File f = new File(STORAGE_PATH + System.getProperty("file.separator")
                    + configurationName);
            fis = new FileInputStream(f);
            in = new ObjectInputStream(fis);
            Configuration config = (Configuration) in.readObject();
            in.close();

            Configuration.setInstance(config);

            return Configuration.getInstance();
        }
        catch (IOException e) {
            return new Configuration();
        }
        catch (ClassNotFoundException e) {
            return new Configuration();
        }
    }

    /**
     * @return the colorConstants
     */
    public ColorConstants getColorConstants() {
        return colorConstants;
    }

    /**
     * @param colorConstants
     *            the colorConstants to set
     */
    public void setColorConstants(ColorConstants colorConstants) {
        this.colorConstants = colorConstants;
    }

    /**
     * @return the fullScreen
     */
    public final boolean isFullScreen() {
        return fullScreen;
    }

    /**
     * @param fullScreen
     *            the fullScreen to set
     */
    public final void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    /**
     * @return the savedDimensionOnFrame
     */
    public final Dimension getSavedDimensionOnFrame() {
        if (savedDimensionOnFrame == null)
            return new Dimension(1100, 800);

        return savedDimensionOnFrame;
    }

    /**
     * @param savedDimensionOnFrame
     *            the savedDimensionOnFrame to set
     */
    public final void setSavedDimensionOnFrame(Dimension savedDimensionOnFrame) {
        this.savedDimensionOnFrame = savedDimensionOnFrame;
    }

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

    /**
     * @return the fontBalancer
     */
    public final float getFontBalancer() {
        return fontBalancer;
    }

    /**
     * @param fontBalancer
     *            the fontBalancer to set
     */
    public final void setFontBalancer(float fontBalancer) {
        this.fontBalancer = fontBalancer;
    }
}
