package com.quiltplayer.internal.id3;

import java.io.IOException;
import java.util.List;

import com.quiltplayer.internal.id3.model.Id3DataModel;

/**
 * Interface for modifying id3 tags.
 * 
 * @author Vlado Palczynski
 */
public interface Id3Modifier {

    /**
     * Modify id3 tags.
     * 
     * @param models
     *            List containing all the modified models. These will get iterated and the ID3
     *            information will get updated.
     * @throws IOException
     *             if something goes wrong updating.
     */
    void modifyId3Tags(List<Id3DataModel> model) throws IOException;
}
