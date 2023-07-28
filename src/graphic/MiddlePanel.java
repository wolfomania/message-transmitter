package graphic;

import events.IntelToGraphic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class MiddlePanel extends JPanel{

    private JPanel main;
    private JButton addLayer;
    private JButton removeLayer;
    private JPanel buttons;

    private StationLayerPanel leftBaseStationLayer;

    private StationLayerPanel middleControlStationLayer;

    private StationLayerPanel rightBaseStationLayer;

    private JScrollPane jScrollPane;

    private JPanel scrollPanel;

    private IntelToGraphic middleLayerIntel;

    public MiddlePanel() {
        setLayout(new BorderLayout());
        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
        main.setBorder(new EtchedBorder());
        main.setPreferredSize(new Dimension(600, 1080));

        leftBaseStationLayer = new StationLayerPanel();
        leftBaseStationLayer.setPreferredSize(new Dimension(100, 1080));

        scrollPanel = new JPanel();
        middleControlStationLayer = new StationLayerPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.X_AXIS));
        scrollPanel.add(middleControlStationLayer);
        jScrollPane = new JScrollPane(scrollPanel);

        rightBaseStationLayer = new StationLayerPanel();
        rightBaseStationLayer.setPreferredSize(new Dimension(100, 1080));

        buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2));
        addLayer = new JButton("+");
        addLayer.addActionListener(e -> {
            if (middleLayerIntel != null) {
                middleLayerIntel.addStationLayer();
                scrollPanel.add(new StationLayerPanel(), 0);
                scrollPanel.revalidate();
                scrollPanel.repaint();
            } else MyGraphics.showError("Logic was not Found", "Missing logic", 2000);
        });
        removeLayer = new JButton("-");
        removeLayer.addActionListener(e -> {
            if (middleLayerIntel != null) {
                new Thread(() -> {
                    middleLayerIntel.removeStationLayer();
                }).start();
                if(scrollPanel.getComponentCount() > 3) {
                    scrollPanel.remove(scrollPanel.getComponentCount() - 1);
                    scrollPanel.revalidate();
                    scrollPanel.repaint();
                }
            } else MyGraphics.showError("Logic was not Found", "Missing logic", 2000);
        });
        buttons.add(addLayer, BorderLayout.WEST);
        buttons.add(removeLayer, BorderLayout.EAST);

        main.add(leftBaseStationLayer);
        main.add(jScrollPane);
        main.add(rightBaseStationLayer);

        add(main, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

    }

    public StationLayerPanel addLayer() {
        StationLayerPanel temp = new StationLayerPanel();
        scrollPanel.add(temp);
        return temp;
    }

    public void setMiddleLayerIntel(IntelToGraphic middleLayerIntel) {
        this.middleLayerIntel = middleLayerIntel;
    }

}
