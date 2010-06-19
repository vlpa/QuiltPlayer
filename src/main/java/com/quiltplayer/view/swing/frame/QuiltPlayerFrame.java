package com.quiltplayer.view.swing.frame;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.jxlayer.JXGlassPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.utils.ClassPathUtils;
import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.ColorConstantsDark;
import com.quiltplayer.view.swing.listeners.GridListener;
import com.quiltplayer.view.swing.panels.controlpanels.AlbumControlPanel;
import com.quiltplayer.view.swing.panels.controlpanels.AlfabeticControlPane;
import com.quiltplayer.view.swing.panels.controlpanels.ControlPanel;
import com.quiltplayer.view.swing.panels.controlpanels.PlayerControlPanel;
import com.quiltplayer.view.swing.panels.utility.LyricsUtilityPanel;
import com.quiltplayer.view.swing.util.ScreenUtils;
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
    private ControlPanel controlPanel;

    private JComponent ui;

    private ActiveView currentView = ActiveView.ABOUT;

    @Autowired
    private Keyboard keyboardPanel;

    @Autowired
    public LyricsUtilityPanel lyricsPlaylistPanel;

    @Autowired
    private AlfabeticControlPane alfabeticControlPane;

    @Autowired
    private AlbumControlPanel albumControlPanel;

    @Autowired
    private PlayerControlPanel playerControlPanel;

    private JPanel glassPane;

    public boolean playlistPanelVisible = false;

    public boolean editAlbumPanelVisible = false;

    public boolean lyricsPanelVisible = false;

    private JPanel utilityPanel;

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

        // PlatformDefaults.setHorizontalScaleFactor(scaleFactor);
        // PlatformDefaults.setVerticalScaleFactor(scaleFactor);

        PlatformDefaults.setDefaultDPI((int) swingDPI);

        System.out.println("DPI from PlatformDefaults: " + PlatformDefaults.getDefaultDPI());
        System.out.println("DPI from Tookit: " + Toolkit.getDefaultToolkit().getScreenResolution());

        getContentPane().setLayout(new MigLayout("fill"));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ScreenUtils.setScreensize(this);

    }

    @PostConstruct
    public void init() {

        setupUtilityPanel();
        setupGridGlassPane();

        ui = aboutView.getUI();

        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                if (!Configuration.getInstance().isFullScreen())
                    Configuration.getInstance().setSavedDimensionOnFrame(getSize());

                repaintComponentsIfResizeAware();
            }
        });

        getContentPane().setBackground(ColorConstantsDark.BACKGROUND);

        updateUI();
    }

    private void setupUtilityPanel() {
        utilityPanel = new JPanel(new MigLayout("ins 0, wrap 4"));
        utilityPanel.setOpaque(false);
    }

    private void setupGridGlassPane() {
        setGlassPane(new JXGlassPane());

        // setGlassPane(new JXGlassPane() {
        // private static final long serialVersionUID = 1L;
        //
        // /*
        // * (non-Javadoc)
        // *
        // * @see org.jdesktop.jxlayer.JXGlassPane#contains(int, int)
        // */
        // @Override
        // public boolean contains(int x, int y) {
        // if (y > this.getHeight() - 150)
        // playerControlPanel.show();
        // else
        // playerControlPanel.hide();
        //
        // return super.contains(x, y);
        // }
        //
        // });

        glassPane = (JXGlassPane) this.getGlassPane();

        glassPane.setLayout(new MigLayout("insets 0, fill"));

        glassPane.add(controlPanel, "west, w 1.7cm!");
        glassPane.add(albumControlPanel, "east, w 1.7cm!");

        glassPane.add(utilityPanel, "east, h 100%, growx");
        glassPane.add(playerControlPanel, "east, h 100%, bottom");
        playerControlPanel.show();
        glassPane.add(keyboardPanel, "south, alignx center");

        glassPane.setVisible(true);
    }

    public Component getUI() {
        return ui;
    }

    public void setUI(JComponent ui) {
        this.ui = ui;
    }

    public void updateUI(ActiveView view) {
        currentView = view;

        updateUI();
    }

    public void updateUI() {
        if (ui != null) {
            remove(ui);
            glassPane.remove(alfabeticControlPane);
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

        if (currentView.equals(ActiveView.COVERS))
            getContentPane().add(ui, "h 100%, w 100%, gapx 0cm 0cm");
        else if (playlistPanelVisible)
            getContentPane().add(ui, "h 100%, w 100%, gapx 0cm 0cm");
        else
            getContentPane().add(ui, "h 100%, w 100%, gapx 0cm 0cm");

        ui.updateUI();

        repaint();

        SwingUtilities.updateComponentTreeUI(ui);
    }

    private void addAlfabeticControlPanel() {
        glassPane.add(alfabeticControlPane, "north,  h 1.2cm!, gapy 0.75cm");
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

    protected void repaintComponentsIfResizeAware() {
        // if (albumPlaylistPanel != null)
        // albumPlaylistPanel.updateUI();

        updateUI();
    }

    public JPanel getUtilityPanel() {
        return utilityPanel;
    }
}
