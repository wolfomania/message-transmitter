import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StationLayerPanel extends JPanel {

    private static int layerPanelCounter = 0;

    private final int layerPanelIndex;
    private JScrollPane jScrollPane;

    private JPanel stations;

    public StationLayerPanel() {
        layerPanelIndex = ++layerPanelCounter;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        setBorder(new EtchedBorder());
        setPreferredSize(new Dimension(200, 600));
        stations = new JPanel();
        jScrollPane = new JScrollPane(stations);
        add(jScrollPane);
    }

    private class StationPanel extends StationPanelTemplate {

        private JLabel jLabel;

        protected JButton jButton;

        public StationPanel() {
            super();
            setLayout(new BorderLayout());
            setBorder(new EtchedBorder());

            jButton = new JButton("Terminate");
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            });
            jLabel = new JLabel("" + stationIndex);

            add(jButton, BorderLayout.SOUTH);
            add(jLabel, BorderLayout.NORTH);
        }
    }

    public StationPanel addStationPanel() {
        StationPanel temp = new StationPanel();
        stations.add(temp);
        return temp;
    }
}
