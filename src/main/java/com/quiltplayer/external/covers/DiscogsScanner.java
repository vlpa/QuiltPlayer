package com.quiltplayer.external.covers;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.quiltplayer.external.covers.discogs.Album;
import com.quiltplayer.external.covers.discogs.Resp;
import com.quiltplayer.external.covers.exception.RequestOverFlowException;

/**
 * Searches for artist and album information on Discogs.
 * 
 * @author Vlado Palczynski
 * 
 */
@Component
public class DiscogsScanner {

    private WebServiceAlbum webServiceAlbum;

    private WebServiceArtist webServiceArtist;

    private SelectionCriterias criterias;

    public DiscogsScanner() {
        this.webServiceAlbum = new WebServiceAlbum();
        this.webServiceArtist = new WebServiceArtist();
        criterias = new SelectionCriterias();
    }

    public Album scanForAlbum(String artistName, String albumTitle, int songs, File storagePath)
            throws IOException, RequestOverFlowException, Exception {
        Resp resp = webServiceArtist.getArtistInfo(artistName, albumTitle);

        checkRequestCount(resp);

        if (resp != null && resp.getArtist() != null) {
            resp = criterias.startSelection(resp, albumTitle);
        }

        checkRequestCount(resp);

        return webServiceAlbum.getAlbumFromDiscogs(resp, albumTitle, songs, storagePath);
    }

    private void checkRequestCount(Resp resp) throws RequestOverFlowException {
        if (Integer.parseInt(resp.getRequests()) > 4999)
            throw new RequestOverFlowException();

    }
}
