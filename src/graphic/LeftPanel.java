package graphic;

import events.IntelToGraphic;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel{

    private IntelToGraphic leftPanelIntel;
    private JScrollPane scrollPane;
    private JButton jButton;
    private JPanel devices;

    public LeftPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 720));
        devices = new JPanel();
        devices.setLayout(new BoxLayout(devices,BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(devices);
        jButton = new JButton("Add");
        jButton.addActionListener(e -> {
            if(leftPanelIntel != null) {
                String message = JOptionPane.showInputDialog("Enter a short text message:");
                if (message != null && !message.isEmpty()) {
                    leftPanelIntel.addVirtualSendingDevice(message, 1);
                    devices.add(new VirtualSendingDevicePanel(leftPanelIntel));
                    revalidate();
                    repaint();
                }
            } else MyGraphics.showError("Logical part is not connected", "Error Message", 1000);
        });
        add(scrollPane, BorderLayout.CENTER);
        add(jButton, BorderLayout.SOUTH);
    }

    public void setLeftPanelIntel(IntelToGraphic leftPanelIntel) {
        this.leftPanelIntel = leftPanelIntel;
    }



}
