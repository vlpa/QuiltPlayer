package com.quiltplayer.properties.configurations;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class FolderProperties implements Serializable {

    private static final long serialVersionUID = 4815833487858367241L;

    private static final Logger log = Logger.getLogger(FolderProperties.class);

    private String userHome = System.getProperty("user.home");

    private String rootFolder = "/.quiltplayer";

    private String storageFolder = "/storage";

    private String coversFolder = "/covers";

    private String musicPath = null;

    /**
     * Location for the root folder.
     */
    private File root = new File(userHome, rootFolder);

    /**
     * Location for the storage repository.
     */
    private File storage = new File(root, storageFolder);

    /**
     * Location for the down loaded album covers.
     */
    private File covers = new File(root, coversFolder);

    public FolderProperties() {
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

    /**
     * @return the root
     */
    public File getRoot() {
        return root;
    }

    /**
     * @return the storage
     */
    public final File getStorage() {
        return storage;
    }

    /**
     * @return the covers
     */
    public final File getCovers() {
        return covers;
    }

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

}
