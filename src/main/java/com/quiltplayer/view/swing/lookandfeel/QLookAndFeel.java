package com.quiltplayer.view.swing.lookandfeel;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicLookAndFeel;

import com.quiltplayer.view.swing.FontFactory;

/**
 * Could be the greatest Look&Feel created.
 * 
 * @author Vlado Palczynski
 * 
 */
public class QLookAndFeel extends BasicLookAndFeel {
    private static final long serialVersionUID = 1L;

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getID() {
        return "quilt";
    }

    @Override
    public String getName() {
        return "Quilt";
    }

    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }

    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.basic.BasicLookAndFeel#initComponentDefaults(javax.swing .UIDefaults)
     */
    @Override
    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);

        Object[] defaults = {

        "Button.font", FontFactory.getSansFont(14f), "Button.background",
                new ColorUIResource(40, 40, 40), "Button.foreground",
                new ColorUIResource(200, 200, 200) };

        table.putDefaults(defaults);

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.basic.BasicLookAndFeel#initClassDefaults(javax.swing .UIDefaults)
     */
    @Override
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);

        try {
            String className = "com.quiltplayer.view.swing.buttons.QButtonUI";

            Class<?> buttonClass = Class.forName(className);

            table.put("ButtonUI", className);
            table.put(className, buttonClass);
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
