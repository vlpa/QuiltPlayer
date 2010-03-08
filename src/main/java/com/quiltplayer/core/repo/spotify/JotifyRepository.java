package com.quiltplayer.core.repo.spotify;

import org.springframework.stereotype.Repository;

import com.quiltplayer.properties.Configuration;

import de.felixbruns.jotify.Jotify;
import de.felixbruns.jotify.JotifyPool;
import de.felixbruns.jotify.exceptions.AuthenticationException;
import de.felixbruns.jotify.exceptions.ConnectionException;

/**
 * Wrapper class for JotifyPool.
 * 
 * @author Vlado Palczynski
 * 
 */
@Repository
public class JotifyRepository {

    private static JotifyPool jotifyPool = new JotifyPool(3);

    private static boolean loggedIn;

    public static synchronized Jotify getInstance() {
        jotifyPool = JotifyPool.getInstance();

        if (!loggedIn) {
            try {
                JotifyPool.getInstance().login(
                        Configuration.getInstance().getSpotifyProperties().getSpotifyUserName(),
                        Configuration.getInstance().getSpotifyProperties().getSpotifyPassword());

                loggedIn = true;
            }
            catch (ConnectionException e) {
                e.printStackTrace();
            }
            catch (AuthenticationException e) {
                e.printStackTrace();
            }
        }

        return jotifyPool;
    }
}
