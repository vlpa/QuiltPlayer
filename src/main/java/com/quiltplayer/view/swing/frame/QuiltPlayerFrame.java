package com.quiltplayer.view.swing.frame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.layout.PlatformDefaults;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.quiltplayer.controller.ControlPanelController;
import com.quiltplayer.external.covers.model.ImageSizes;
import com.quiltplayer.model.Album;
import com.quiltplayer.properties.Configuration;
import com.quiltplayer.view.swing.ActiveView;
import com.quiltplayer.view.swing.buttons.QTextButton;
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
    // private VolumePane volumePane;

    private Component ui;

    private ActiveView currentView = ActiveView.ABOUT_VIEW;

    @Autowired
    private KeyboardPanel keyboardPanel;

    @Autowired
    private ControlPanelController controlPanelListener;

    private JPanel glassPane;

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

        setLayout(new MigLayout("insets 10"));

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

        /**
         * Playlist view
         */
        getContentPane().add(playlistPanel,
                "cell 0 0, h 100%, dock west, w " + (ImageSizes.LARGE.getSize() + 90) + "px!");

        ui = aboutView.getUI();
        getContentPane().add(ui, "cell 2 0, w 68%, h 100%, gapx 0");

        // DebugRepaintingUI debugUI = new DebugRepaintingUI();
        // JXLayer<JComponent> layer = new JXLayer<JComponent>(controlPanel,
        // debugUI);

        getContentPane().add(controlPanel, "cell 1 0, dock east, alignx center");
        // glassPane.add(controlPanel, "w 200lpx");

        updateUI();
    }

    private void setupGridGlassPane() {

        glassPane = (JPanel) this.getGlassPane();
        glassPane.setLayout(new MigLayout("insets 0, fill"));

        JButton increaseGridButton = new QTextButton("[ + ]");
        increaseGridButton.addActionListener(controlPanelListener);
        increaseGridButton.setActionCommand(ControlPanelController.EVENT_INCREASE_GRID);
        increaseGridButton.setToolTipText("Add column in the grid above");
        increaseGridButton.setBorderPainted(false);

        JButton decreaseGridButton = new QTextButton("[ - ]");
        decreaseGridButton.addActionListener(controlPanelListener);
        decreaseGridButton.setActionCommand(ControlPanelController.EVENT_DECREASE_GRID);
        decreaseGridButton.setToolTipText("Remove column in the grid above");
        decreaseGridButton.setBorderPainted(false);

        JPanel panel = new JPanel(new MigLayout("insets 0, wrap 1"));
        panel.add(increaseGridButton, "right, gapy 0 20, gapx 0 30");
        panel.add(decreaseGridButton, "right, gapy 20 50, gapx 0 30 ");
        panel.setOpaque(false);

        glassPane.add(panel, "right, bottom");
        glassPane.add(keyboardPanel, "center");
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
            showGlassPane();
        }
        else if (currentView.equals(ActiveView.ALFABETIC_ARTISTS_VIEW)) {
            ui = artistView.getUI();
            showGlassPane();
        }

        else if (currentView.equals(ActiveView.ALBUM_VIEW)) {
            ui = albumView.getUI();
            controlPanel.updateTab(null);
            showGlassPane();
        }
        else if (currentView.equals(ActiveView.SEARCH_VIEW)) {
            ui = searchView.getUI();
            hideGlassPane();
        }
        else if (currentView.equals(ActiveView.CONFIGURATION_VIEW)) {
            ui = configurationView.getUI();
            hideGlassPane();
        }
        else if (currentView.equals(ActiveView.ABOUT_VIEW)) {
            ui = aboutView.getUI();
            glassPane.updateUI();
            controlPanel.updateTab(null);
            hideGlassPane();
        }
        else if (currentView.equals(ActiveView.EDIT_ALBUM_VIEW)) {
            ui = editAlbumView.getUI();
            controlPanel.updateTab(null);
            hideGlassPane();
        }

        getContentPane().add(ui, "cell 2 0, w 68%, h 100%, gapx 0");
        SwingUtilities.updateComponentTreeUI(this);
    }

    private void showGlassPane() {
        glassPane.setVisible(true);
    }

    private void hideGlassPane() {
        glassPane.setVisible(false);
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

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        super.paint(g);
    }

    /**
     * @param controlPanelListener
     *            the controlPanelListener to set
     */
    public final void setControlPanelListener(ControlPanelController controlPanelListener) {
        this.controlPanelListener = controlPanelListener;
    }
}
