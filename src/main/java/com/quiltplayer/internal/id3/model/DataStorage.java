package com.quiltplayer.internal.id3.model;

public interface DataStorage {

    void store(Id3DataModel model);

    void progress(int counter);
}
