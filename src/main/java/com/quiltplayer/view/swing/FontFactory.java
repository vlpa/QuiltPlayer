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

    private static Font largeFont;

    static {
        try {
            log.debug("Creating fonts...");

            Resource file = new ClassPathResource("DejaVuSansCondensed.ttf");

            URL url = file.getURL();

            font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());

            sansFont = font;

            file = new ClassPathResource("DejaVuSans-ExtraLight.ttf");

            url = file.getURL();

            largeFont = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static Font getFont(float size) {
        float fontSize = size + Configuration.getInstance().getFontBalancer() + 1;

        return font.deriveFont(fontSize);
    }

    public static Font getLargeTextFont(float size) {
        float fontSize = size + Configuration.getInstance().getFontBalancer();

        return sansFont.deriveFont(fontSize);
    }
}
