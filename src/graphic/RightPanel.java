package graphic;

import events.IntelToGraphic;

import javax.swing.*;
import java.awt.*;

public class RightPanel extends JPanel {
    private JScrollPane scrollPane;
    private JButton jButton;
    private JPanel devices;

    private IntelToGraphic rightPanelIntel;

    public RightPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 720));
        devices = new JPanel();
        devices.setLayout(new BoxLayout(devices,BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(devices);
        jButton = new JButton("Add");
        jButton.addActionListener(e -> {
            if(rightPanelIntel != null) {
//                graphic.VirtualReceivingDevicePanel.getDeviceList().add(new graphic.VirtualReceivingDevicePanel(rightPanelIntel));
//                devices.add(graphic.VirtualReceivingDevicePanel.getDeviceList().get(graphic.VirtualReceivingDevicePanel.getDeviceList().size() - 1));
                VirtualReceivingDevicePanel receivingDevicePanel = new VirtualReceivingDevicePanel(rightPanelIntel);
                devices.add(receivingDevicePanel);
                rightPanelIntel.addReceivingDevice(receivingDevicePanel);
                revalidate();
                repaint();
            } else MyGraphics.showError("Logic is not connected. Please Connect logic.MyLogic to MyGraphic", "Error Message", 2000);
        });
        add(scrollPane, BorderLayout.CENTER);
        add(jButton, BorderLayout.SOUTH);
    }

    public void setRightPanelIntel(IntelToGraphic rightPanelIntel) {
        this.rightPanelIntel = rightPanelIntel;
    }
}
