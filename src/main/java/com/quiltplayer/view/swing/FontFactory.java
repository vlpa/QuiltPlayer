package com.quiltplayer.view.swing;

import java.awt.Font;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.quiltplayer.properties.Configuration;

public class FontFactory {
    private static Logger log = Logger.getLogger(FontFactory.class);

    private static Font font;

    private static Font sansFont;

    static {
        try {
            log.debug("Creating fonts...");

            Resource file = new ClassPathResource("ae_Electron.ttf");

            URL url = file.getURL();

            font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());

            file = new ClassPathResource("FreeSans.ttf");

            url = file.getURL();

            sansFont = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static Font getFont(float size) {
        float fontSize = size
                + Configuration.getInstance().getFontBalancer();

        return font.deriveFont(fontSize);
    }

    public static Font getSansFont(float size) {
        float fontSize = size
                + Configuration.getInstance().getFontBalancer();

        return sansFont.deriveFont(fontSize);
    }
}
