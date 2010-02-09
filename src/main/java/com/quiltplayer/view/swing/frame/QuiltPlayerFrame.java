package com.quiltplayer.view.swing.frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.panels.ControlPanel;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.views.ArtistView;
import com.quiltplayer.view.swing.views.ListView;
import com.quiltplayer.view.swing.views.View;
import com.quiltplayer.view.swing.views.impl.ConfigurationView;
import com.quiltplayer.view.swing.window.KeyboardPanel;

/**
 * Main Frame for QuiltPlayer.
 * 
 * @author Vlado Palczynski
 */
@org.springframework.stereotype.Component()
public class QuiltPlayerFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private Configuration config = Configuration.getInstance();

    @Autowired
    @Qualifier("defaultAlbumView")
    private ListView<Album> albumView;

    @Autowired
    private View searchView;

    @Autowired
    @Qualifier("quiltView")
    private ListView<Album> quiltView;

    @Autowired
    @Qualifier("defaultArtistView")
    private ArtistView artistView;

    @Autowired
    private ConfigurationView configurationView;

    @Autowired
    private View aboutView;

    @Autowired
    private View editAlbumView;

    @Autowired
    private PlaylistPanel playlistPanel;

    @Autowired
    private ControlPanel controlPanel;

    private Component ui;

    private ActiveView currentView = ActiveView.ABOUT_VIEW;

    @Autowired
    private KeyboardPanel keyboardPanel;

    private JPanel glassPane;

    private boolean b = true;

    public QuiltPlayerFrame() {
        float swingDPI = Toolkit.getDefaultToolkit().getScreenResolution();
        float migDPI = PlatformDefaults.getDefaultDPI();

        float scaleFactor = migDPI / swingDPI;

        System.out.println("Screen size: " + Toolkit.getDefaultToolkit().getScreenSize());

        System.out.println("ImageSize small: " + ImageSizes.SMALL.getSize());

        System.out.println("ImageSize medium: " + ImageSizes.MEDIUM.getSize());

        System.out.println("ImageSize large: " + ImageSizes.LARGE.getSize());

        System.out.println("ScaleFactor: " + scaleFactor);
        System.out.println("Large image size in px: " + ImageSizes.LARGE.getSize());

        PlatformDefaults.setHorizontalScaleFactor(scaleFactor);
        PlatformDefaults.setVerticalScaleFactor(scaleFactor);

        PlatformDefaults.setDefaultHorizontalUnit(UnitValue.LPX);
        PlatformDefaults.setDefaultVerticalUnit(UnitValue.LPY);

        System.out.println("DPI from PlatformDefaults: " + PlatformDefaults.getDefaultDPI());
        System.out.println("DPI from Tookit: " + Toolkit.getDefaultToolkit().getScreenResolution());

        setLayout(new MigLayout("insets 0, fill, w 100%, h 100%"));

        setTitle("QuiltPlayer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension screenSize = null;
        if (Configuration.getInstance().isFullScreen()) {
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            setUndecorated(true);
            setResizable(false);
            setLocation(0, 0);
        }
        else {
            screenSize = Configuration.getInstance().getSavedDimensionOnFrame();
            Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
            setLocation(fullScreen.width / 2 - ((int) screenSize.getWidth() / 2), fullScreen.height
                    / 2 - ((int) screenSize.getHeight() / 2));
        }

        setSize(screenSize);
        setVisible(true);

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                if (!Configuration.getInstance().isFullScreen())
                    Configuration.getInstance().setSavedDimensionOnFrame(getSize());
            }
        });
    }

    @PostConstruct
    public void init() {

        setupGridGlassPane();

        ui = aboutView.getUI();

        // DebugRepaintingUI debugUI = new DebugRepaintingUI();
        // JXLayer<JComponent> layer = new JXLayer<JComponent>(controlPanel,
        // debugUI);

        getContentPane().add(controlPanel, "cell 0 0, dock north, h 1.5cm!");
        controlPanel.getPlayerControlPanel().setStopped();

        addAlbumView();

        updateUI();
    }

    private void setupGridGlassPane() {

        glassPane = (JPanel) this.getGlassPane();
        glassPane.setLayout(new MigLayout("insets 0, fill"));

        glassPane.add(keyboardPanel, "center");

        glassPane.setVisible(true);
    }

    public Component getUI() {
        return ui;
    }

    public void setUI(Component ui) {
        this.ui = ui;
    }

    public void updateUI(ActiveView view) {

        // if (currentView.equals(ActiveView.QUILT_VIEW))
        // quiltView.close();
        // else if (currentView.equals(ActiveView.ALFABETIC_ARTISTS_VIEW))
        // artistView.close();
        // else if (currentView.equals(ActiveView.ALBUM_VIEW))
        // albumView.close();
        // else if (currentView.equals(ActiveView.SEARCH_VIEW))
        // searchView.close();
        // else if (currentView.equals(ActiveView.CONFIGURATION_VIEW))
        // configurationView.close();
        // else if (currentView.equals(ActiveView.ABOUT_VIEW))
        // aboutView.close();
        // else if (currentView.equals(ActiveView.EDIT_ALBUM_VIEW))
        // editAlbumView.close();

        currentView = view;

        updateUI();
    }

    public void updateUI() {
        if (ui != null) {
            remove(ui);
        }

        if (currentView.equals(ActiveView.QUILT_VIEW)) {
            ui = quiltView.getUI();
        }
        else if (currentView.equals(ActiveView.ALFABETIC_ARTISTS_VIEW)) {
            ui = artistView.getUI();
        }

        else if (currentView.equals(ActiveView.ALBUM_VIEW)) {
            ui = albumView.getUI();
            controlPanel.updateTab(null);
        }
        else if (currentView.equals(ActiveView.SEARCH_VIEW)) {
            ui = searchView.getUI();
        }
        else if (currentView.equals(ActiveView.CONFIGURATION_VIEW)) {
            ui = configurationView.getUI();
        }
        else if (currentView.equals(ActiveView.ABOUT_VIEW)) {
            ui = aboutView.getUI();
            controlPanel.updateTab(null);
        }
        else if (currentView.equals(ActiveView.EDIT_ALBUM_VIEW)) {
            ui = editAlbumView.getUI();
            controlPanel.updateTab(null);
        }

        getContentPane().add(ui, "cell 2 0, grow");

        ui.repaint();
        repaint();

    }

    public ActiveView getCurrentView() {
        return currentView;
    }

    public Configuration getConfigurationProperties() {
        return config;
    }

    public View getAboutView() {
        return aboutView;
    }

    public View getSearchView() {
        return searchView;
    }

    public ListView getAlbumView() {
        return albumView;
    }

    /**
     * @param searchView
     *            the searchView to set
     */
    public final void setSearchView(View searchView) {
        this.searchView = searchView;
    }

    public void toggleAlbumView() {
        if (b) {
            remove(playlistPanel);
            controlPanel.albumViewButton.inactivate();
            b = false;
        }
        else {
            addAlbumView();
            controlPanel.albumViewButton.activate();
            b = true;
        }

        repaint();
        updateUI();
    }

    private void addAlbumView() {
        getContentPane().add(playlistPanel, "cell 1 0, dock west, growx");
    }
}
