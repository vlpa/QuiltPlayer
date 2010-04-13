package com.quiltplayer.view.swing.frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.quiltplayer.controller.GridController;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.utils.ClassPathUtils;
import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.buttons.QTextButton;
import com.quiltplayer.view.swing.listeners.GridListener;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.panels.controlpanels.AlbumControlPanel;
import com.quiltplayer.view.swing.panels.controlpanels.AlfabeticControlPane;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;
import com.quiltplayer.view.swing.views.ArtistView;
import com.quiltplayer.view.swing.views.ListView;
import com.quiltplayer.view.swing.views.View;
import com.quiltplayer.view.swing.views.impl.ConfigurationView;
import com.quiltplayer.view.swing.window.Keyboard;

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
    @Qualifier("wikiView")
    private View wikiView;

    @Autowired
    @Qualifier("albumArtView")
    private View albumArtView;

    @Autowired
    @Qualifier("defaultArtistView")
    private ArtistView artistView;

    @Autowired
    private ConfigurationView configurationView;

    @Autowired
    private GridListener gridListener;

    @Autowired
    private View aboutView;

    @Autowired
    private PlaylistPanel playlistPanel;

    @Autowired
    private ControlPanel controlPanel;

    private JComponent ui;

    private ActiveView currentView = ActiveView.ABOUT;

    @Autowired
    private Keyboard keyboardPanel;

    @Autowired
    private AlfabeticControlPane alfabeticControlPane;

    @Autowired
    private AlbumControlPanel albumControlPanel;

    private JPanel glassPane;

    public boolean playlistPanelVisible = false;

    public QuiltPlayerFrame() {
        setTitle("QuiltPlayer");
        setIconImage(ClassPathUtils.getIconFromClasspath("icon/quilticon.gif").getImage());

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

        getContentPane().setLayout(new MigLayout("fill"));

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

                repaintComponentsIfResizeAware();
            }
        });
    }

    @PostConstruct
    public void init() {

        setupGridGlassPane();

        ui = aboutView.getUI();

        // getContentPane().add(controlPanel, "east, w 1.6cm!");
        // getContentPane().add(albumControlPanel, "west, w 1.6cm!");

        updateUI();
    }

    private void setupGridGlassPane() {

        glassPane = (JPanel) this.getGlassPane();
        glassPane.setLayout(new MigLayout("insets 0, fill"));

        glassPane.add(controlPanel, "east, w 1.6cm!");
        glassPane.add(albumControlPanel, "west, w 1.6cm!");

        JButton increaseGridButton = new QTextButton("[ + ]");
        increaseGridButton.addActionListener(gridListener);
        increaseGridButton.setActionCommand(GridController.EVENT_DECREASE_GRID);
        increaseGridButton.setToolTipText("Bigger");
        increaseGridButton.setBorderPainted(false);

        JButton decreaseGridButton = new QTextButton("[ - ]");
        decreaseGridButton.addActionListener(gridListener);
        decreaseGridButton.setActionCommand(GridController.EVENT_INCREASE_GRID);
        decreaseGridButton.setToolTipText("Smaller");
        decreaseGridButton.setBorderPainted(false);

        // glassPane.add(increaseGridButton, "top, gapx 55% 1%, gapy 5%");
        // glassPane.add(decreaseGridButton, "top, gapx 0% 7%, gapy 5%");

        // glassPane.add(keyboardPanel, "west");

        glassPane.setVisible(true);
    }

    public Component getUI() {
        return ui;
    }

    public void setUI(JComponent ui) {
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
            getContentPane().remove(alfabeticControlPane);
        }

        if (currentView.equals(ActiveView.QUILT)) {
            ui = quiltView.getUI();
            addAlfabeticControlPanel();
        }
        else if (currentView.equals(ActiveView.WIKI)) {
            ui = wikiView.getUI();
        }
        else if (currentView.equals(ActiveView.COVERS)) {
            ui = albumArtView.getUI();
        }
        else if (currentView.equals(ActiveView.ARTISTS)) {
            ui = artistView.getUI();
        }
        else if (currentView.equals(ActiveView.ALBUMS)) {
            ui = albumView.getUI();
            controlPanel.updateTab(null);
        }
        else if (currentView.equals(ActiveView.SEARCH)) {
            ui = searchView.getUI();
        }
        else if (currentView.equals(ActiveView.CONFIGURATION)) {
            ui = configurationView.getUI();
        }
        else if (currentView.equals(ActiveView.ABOUT)) {
            ui = aboutView.getUI();
        }

        getContentPane().add(ui, "h 100%, w 100%");

        repaint();

        SwingUtilities.updateComponentTreeUI(ui);
    }

    private void addAlfabeticControlPanel() {
        getContentPane().add(alfabeticControlPane, "east, right, w 1cm!, gapx 0 1.6cm!");
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

    /**
     * @param searchView
     *            the searchView to set
     */
    public final void setSearchView(View searchView) {
        this.searchView = searchView;
    }

    public void removeAlbumView() {
        if (playlistPanelVisible) {
            remove(playlistPanel);
            playlistPanelVisible = false;
        }
    }

    public void toggleAlbumView() {
        if (playlistPanelVisible) {
            glassPane.remove(playlistPanel);
            playlistPanelVisible = false;
        }
        else {
            addPlaylistView();
            playlistPanelVisible = true;
        }

        playlistPanel.updateUI();
        ui.repaint();
    }

    private void addPlaylistView() {
        glassPane.add(playlistPanel, "dock west, w 28%!, ");
    }

    protected void repaintComponentsIfResizeAware() {
        if (playlistPanel != null)
            playlistPanel.updateUI();

        updateUI();
    }
}
