package com.quiltplayer.internal.id3.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import com.quiltplayer.internal.id3.Id3Extractor;
import com.quiltplayer.internal.id3.model.DataStorage;
import com.quiltplayer.internal.id3.model.Id3DataModel;

/**
 * General ID3 extractor using mp3spi.jar. Error handling is limited but the general idea is that the
 * failed files should be published in the application.
 * 
 * @author Vlado Palczynski
 */
@Component
public class Mp3SpiId3Extractor implements Id3Extractor {

    private Pattern p = Pattern.compile("^[0-9]+");

    private DataStorage storage;

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Extractor#extractFile(java.io.File)
     */
    public void extractId3Tags(Collection<File> files, DataStorage storage) {
        this.storage = storage;

        int i = 0;

        for (File file : files) {
            extract(file);
            storage.progress(++i);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.quiltplayer.id3utils.Id3Extractor#extractByAlbum(java.util.List)
     */
    public Collection<Id3DataModel> extractId3Tags(Collection<File> files) {

        List<Id3DataModel> l = new ArrayList<Id3DataModel>();

        for (File file : files) {
            extract(file);
        }

        return l;
    }

    /**
     * Storage for unsuccessful entries.
     */
    private List<File> unsuccessfull = new ArrayList<File>();

    /**
     * Logger.
     */
    private static Logger log = Logger.getLogger(Mp3SpiId3Extractor.class);

    /**
     * @param file
     *            the file to extract from.
     * @return Id3DataModel
     */
    private void extract(final File file) {
        String albumTitle = null;
        String artistName = null;
        String songTitle = null;
        String trackNumber = null;
        Long duration = null;

        // Extract information from ID3-tag
        try {
            AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(file);
            if (baseFileFormat instanceof TAudioFileFormat) {
                Map<String, ?> properties = ((TAudioFileFormat) baseFileFormat).properties();

                albumTitle = (String) properties.get("album");
                songTitle = (String) properties.get("title");
                artistName = (String) properties.get("author");
                trackNumber = (String) properties.get("mp3.id3tag.track");
                duration = (Long) properties.get("duration");
            }

            Id3DataModel model = new Id3DataModel();
            model.setAlbumTitle(albumTitle);
            model.setArtistName(artistName);
            model.setSongTitle(songTitle);
            if (trackNumber != null) {
                String fileName = file.getName();

                Matcher m = p.matcher(fileName);
                boolean b = false;
                while (m.find()) {
                    model.setTrackNumber(Integer.parseInt(m.group()));
                    b = true;
                    log.debug("Setting song number to " + m.group());
                }

                if (!b)
                    model.setTrackNumber(Integer.parseInt(trackNumber));
            }
            else {
                /* No good, check the file name if it starts with a number */
                String fileName = file.getName();
                Matcher m = p.matcher(fileName);

                while (m.find()) {
                    model.setTrackNumber(Integer.parseInt(m.group()));

                    log.debug("Setting song number to " + m.group());
                }
            }

            model.setPath(file.getAbsoluteFile());
            model.setDuration(duration.intValue());

            if (storage != null) {
                storage.store(model);
            }
        }
        catch (Exception e) {
            unsuccessfull.add(file);
            log.error(e.getMessage());

            e.printStackTrace();
        }
    }
}