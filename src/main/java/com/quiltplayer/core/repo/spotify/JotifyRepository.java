package com.quiltplayer.core.repo.spotify;

import org.springframework.stereotype.Repository;

import com.quiltplayer.properties.Configuration;

import de.felixbruns.jotify.Jotify;
import de.felixbruns.jotify.JotifyPool;
import de.felixbruns.jotify.exceptions.AuthenticationException;
import de.felixbruns.jotify.exceptions.ConnectionException;

/**
 * 
 * @author vlado
 * 
 */
@Repository
public class JotifyRepository {

    private JotifyPool jotifyPool = new JotifyPool(5);

    private boolean loggedIn;

    public synchronized Jotify getInstance() {
        jotifyPool = JotifyPool.getInstance();

        if (!loggedIn) {
            try {
                this.jotifyPool.login(Configuration.getInstance().getSpotifyUserName(),
                        Configuration.getInstance().getSpotifyPassword());
            }
            catch (ConnectionException e) {
                e.printStackTrace();
            }
            catch (AuthenticationException e) {
                e.printStackTrace();
            }

            loggedIn = true;
        }

        return jotifyPool;
    }
}
