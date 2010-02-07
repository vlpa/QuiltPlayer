package com.quiltplayer.internal.id3.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.cmc.music.metadata.IMusicMetadata;
import org.cmc.music.metadata.MusicMetadata;
import org.cmc.music.metadata.MusicMetadataSet;
import org.cmc.music.myid3.MyID3;
import org.springframework.stereotype.Component;

import com.quiltplayer.internal.id3.Id3Modifier;
import com.quiltplayer.internal.id3.model.Id3DataModel;

/**
 * General ID3 modifier.
 * 
 * @author Vlado Palczynski
 */
@Component
public class MyId3Modifier implements Id3Modifier {
    /**
     * The MyID3 instance.
     */
    private MyID3 myID3 = new MyID3();

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Modifier#modifyId3Tags(java.util.List)
     */
    public void modifyId3Tags(final Collection<Id3DataModel> models) throws IOException {

        for (Id3DataModel model : models) {
            try {
                File src = model.getPath();

                MusicMetadataSet src_set = myID3.read(src);

                /* perhaps no metadata */
                if (src_set != null) {
                    IMusicMetadata metadata = src_set.getSimplified();

                    metadata.setArtist(model.getArtistName());
                    metadata.setAlbum(model.getAlbumTitle());

                    myID3.update(src, src_set, (MusicMetadata) metadata);
                }
            }
            catch (Exception e) {
                throw new IOException(e);
            }
        }
    }
}