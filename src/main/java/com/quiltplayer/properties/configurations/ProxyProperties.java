package com.quiltplayer.properties.configurations;

import java.io.Serializable;

public class ProxyProperties implements Serializable {

    private static final long serialVersionUID = 5225407964495282646L;

    private boolean useProxy;

    private int proxyPort;

    private String proxyUrl;

    private String proxyUsername;

    private transient String proxyPassword;

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
}
