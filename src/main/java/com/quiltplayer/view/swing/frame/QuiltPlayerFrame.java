package com.quiltplayer.view.swing.frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXGlassPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.utils.ClassPathUtils;
import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.listeners.GridListener;
import com.quiltplayer.view.swing.panels.PlaylistPanel;
import com.quiltplayer.view.swing.panels.controlpanels.AlbumControlPanel;
import com.quiltplayer.view.swing.panels.controlpanels.AlfabeticControlPane;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;
import com.quiltplayer.view.swing.panels.controlpanels.PlayerControlPanel;
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

    @Autowired
    private PlayerControlPanel playerControlPanel;

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
            setLocation(fullScreen.width / 2 - ((int) screenSize.getWidth() / 2), fullScreen.height / 2
                    - ((int) screenSize.getHeight() / 2));
        }

        setSize(screenSize);
        setVisible(true);
    }

    @PostConstruct
    public void init() {

        setupGridGlassPane();

        ui = aboutView.getUI();

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                if (!Configuration.getInstance().isFullScreen())
                    Configuration.getInstance().setSavedDimensionOnFrame(getSize());

                repaintComponentsIfResizeAware();
            }
        });

        updateUI();
    }

    private void setupGridGlassPane() {

        setGlassPane(new JXGlassPane() {
            /*
             * (non-Javadoc)
             * 
             * @see org.jdesktop.jxlayer.JXGlassPane#contains(int, int)
             */
            @Override
            public boolean contains(int x, int y) {
                if (y < 150)
                    playerControlPanel.show();
                else
                    playerControlPanel.hide();

                return super.contains(x, y);
            }

        });

        glassPane = (JXGlassPane) this.getGlassPane();

        glassPane.setLayout(new MigLayout("insets 0, fill"));

        glassPane.add(controlPanel, "east, w 1.6cm!");
        glassPane.add(albumControlPanel, "west, w 1.6cm!");

        glassPane.add(playerControlPanel, "north, center, gapx 15% 15%");

        glassPane.add(keyboardPanel, "south, center");

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
            getContentPane().remove(playlistPanel);
            playlistPanelVisible = false;
        }
    }

    public void toggleAlbumView() {
        if (playlistPanelVisible) {
            getContentPane().remove(playlistPanel);
            playlistPanelVisible = false;
        }
        else {
            addPlaylistView();
            playlistPanelVisible = true;
        }

        playlistPanel.updateUI();
        updateUI();
    }

    private void addPlaylistView() {
        getContentPane().add(playlistPanel, "dock west, w 28%!, gapx 1.6cm");
    }

    protected void repaintComponentsIfResizeAware() {
        if (playlistPanel != null)
            playlistPanel.updateUI();

        updateUI();
    }
}
