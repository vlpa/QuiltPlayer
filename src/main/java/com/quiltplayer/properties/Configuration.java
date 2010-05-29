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
import org.springframework.stereotype.Component;

import com.quiltplayer.properties.configurations.FolderProperties;
import com.quiltplayer.properties.configurations.GridProperties;
import com.quiltplayer.properties.configurations.ProxyProperties;
import com.quiltplayer.properties.configurations.SpotifyProperties;
import com.quiltplayer.properties.configurations.UiProperties;
import com.quiltplayer.view.swing.ColorConstants;
import com.quiltplayer.view.swing.ColorConstantsDark;

/**
 * Storage of needed user properties.
 * 
 * @author Vlado Palczynski
 */
@Component
public final class Configuration implements Serializable {

    private static final long serialVersionUID = 7625101231832775970L;

    private final transient Logger log = Logger.getLogger(Configuration.class);

    private FolderProperties folderProperties;

    private UiProperties uiProperties;

    private GridProperties gridProperties;

    private SpotifyProperties spotifyProperties;

    private ProxyProperties proxyProperties;

    private static final String CONFIG_NAME = "configuration.ser";

    private static Configuration instance;

    private String apiKey = null;

    private ColorConstants colorConstants = new ColorConstantsDark();

    private boolean fullScreen;

    private Dimension savedDimensionOnFrame;

    private float fontBalancer = 0;

    public static final String lineBreak = System.getProperty("line.separator");

    protected Configuration() {

        instance = retrieveConfiguration();

        if (instance == null) {

            log.debug("Retrieve configuration...");

            folderProperties = new FolderProperties();
            gridProperties = new GridProperties();
            spotifyProperties = new SpotifyProperties();
            uiProperties = new UiProperties();
            proxyProperties = new ProxyProperties();

            storeConfiguration();
            instance = retrieveConfiguration();
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    public Configuration retrieveConfiguration() {
        FileInputStream fis = null;
        ObjectInputStream in = null;

        FolderProperties neededProperties = new FolderProperties();

        try {
            File f = new File(neededProperties.getStorage().getAbsolutePath() + System.getProperty("file.separator")
                    + CONFIG_NAME);
            fis = new FileInputStream(f);
            in = new ObjectInputStream(fis);

            Configuration config = (Configuration) in.readObject();
            in.close();

            return config;
        }
        catch (IOException e) {
            return null;
        }
        catch (ClassNotFoundException e) {
            return new Configuration();
        }
    }

    /*
     * @see org.quiltplayer.core.storage.Storage#storeConfiguration(org.quiltplayer
     * .ConfigurationProperties)
     */
    public synchronized void storeConfiguration() {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        FolderProperties neededProperties = new FolderProperties();

        try {
            File f = new File(neededProperties.getStorage() + System.getProperty("file.separator") + CONFIG_NAME);
            fos = new FileOutputStream(f);
            out = new ObjectOutputStream(fos);
            out.writeObject(this);
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void setInstance(Configuration config) {
        instance = config;
    }

    /**
     * API key for access to Discogs webservice.
     */
    public static final String DISCOGS_API_KEY = "6dfeb90be3";

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
            return new Dimension(800, 600);

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

    /**
     * @return the folderProperties
     */
    public final FolderProperties getFolderProperties() {
        return folderProperties;
    }

    /**
     * @return the uiProperties
     */
    public final UiProperties getUiProperties() {
        return uiProperties;
    }

    /**
     * @return the gridProperties
     */
    public final GridProperties getGridProperties() {
        return gridProperties;
    }

    /**
     * @return the spotifyProperties
     */
    public final SpotifyProperties getSpotifyProperties() {
        return spotifyProperties;
    }

    /**
     * @return the proxyProperties
     */
    public final ProxyProperties getProxyProperties() {
        return proxyProperties;
    }
}
