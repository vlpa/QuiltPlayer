package com.quiltplayer.core.player.tasks;

import javax.swing.ImageIcon;

import com.quiltplayer.external.covers.util.ImageUtils;
import com.quiltplayer.model.Album;
import com.quiltplayer.view.swing.buttons.ImageButton;

/**
 * Task for loading an Image.
 * 
 * @author Vlado Palczynski
 * 
 */
public class ImageLoaderTask implements Runnable {

    private Album album;

    private int width;

    private ImageButton c;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        ImageIcon icon = ImageUtils.scalePicture(new ImageIcon(album.getImages().get(0).getLargeImage()
                .getAbsolutePath()), width);
        c.setImage(icon);
    }

    public ImageLoaderTask(final Album album, int width, ImageButton c) {
        this.album = album;
        this.width = width;
        this.c = c;
    }
}