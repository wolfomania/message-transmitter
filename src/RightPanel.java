import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
//                VirtualReceivingDevicePanel.getDeviceList().add(new VirtualReceivingDevicePanel(rightPanelIntel));
//                devices.add(VirtualReceivingDevicePanel.getDeviceList().get(VirtualReceivingDevicePanel.getDeviceList().size() - 1));
                VirtualReceivingDevicePanel receivingDevicePanel = new VirtualReceivingDevicePanel(rightPanelIntel);
                devices.add(receivingDevicePanel);
                rightPanelIntel.addReceivingDevice(receivingDevicePanel);
                revalidate();
                repaint();
            } else MyGraphics.showError("Logic is not connected. Please Connect MyLogic to MyGraphic", "Error Message", 2000);
        });
        add(scrollPane, BorderLayout.CENTER);
        add(jButton, BorderLayout.SOUTH);
    }

    public void setRightPanelIntel(IntelToGraphic rightPanelIntel) {
        this.rightPanelIntel = rightPanelIntel;
    }
}
