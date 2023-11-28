package graphic;

import events.IntelToGraphic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class VirtualSendingDevicePanel extends JPanel {

    private final JSlider jSlider;
    private final JButton jButton;
    private final JTextField jTextField;
    private final JComboBox<String> jComboBox;
    private volatile boolean isActive;
    private volatile int frequency;
    private static int deviceCounter = 1;

    private final int deviceIndex;

    private final IntelToGraphic sendingDeviceIntel;
    public VirtualSendingDevicePanel(IntelToGraphic intel) {
        sendingDeviceIntel = intel;
        deviceIndex = deviceCounter++;
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(300, 200));
        setMaximumSize(new Dimension(300,200));
        setBorder(new EtchedBorder());

        isActive = true;
        jComboBox = new JComboBox<>(new String[]{"Active", "Waiting"});
        jComboBox.addActionListener(e -> {
            String activity = (String) jComboBox.getSelectedItem();
            if(activity.equals("Active")) {
                isActive = true;
                sendingDeviceIntel.startSendingMessages(deviceIndex);
            }
            else {
                isActive = false;
                sendingDeviceIntel.stopSendingMessages(deviceIndex);
            }

        });

        jSlider = new JSlider(0, 30);
        jSlider.setMajorTickSpacing(10);
        jSlider.setMinorTickSpacing(1);
        jSlider.setValue(1);
        jSlider.setPaintTicks(true);
        jSlider.setPaintLabels(true);
        jSlider.addChangeListener(e -> {
//            frequency = jSlider.getValue();
            SwingUtilities.invokeLater(() -> {
                if(jSlider.getValue() != 0)
                    sendingDeviceIntel.setSendingFrequency(deviceIndex, jSlider.getValue());
                else {
                    jComboBox.setSelectedItem("Waiting");
                    sendingDeviceIntel.stopSendingMessages(deviceIndex);
                    jSlider.setValue(1);
                }
            });
        });

        jButton = new JButton("Terminate");
        jButton.addActionListener(e -> {
                Container parent = getParent();
                SwingUtilities.invokeLater(() -> {
                    sendingDeviceIntel.removeVirtualSendingDevice(deviceIndex);
                    parent.remove(VirtualSendingDevicePanel.this);
                    parent.revalidate();
                    parent.repaint();
                });
        });

        jTextField = new JTextField("" + deviceIndex);
        jTextField.setEditable(false);

        JPanel slider = new JPanel();
        slider.setLayout(new BorderLayout());
        slider.add(new JLabel("Messages per second"), BorderLayout.NORTH);
        slider.add(jSlider, BorderLayout.SOUTH);
        add(slider);

        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());
        button.add(new JLabel("Terminate the device:"));
        button.add(jButton);
        add(button);

        JPanel comboBox = new JPanel();
        comboBox.setLayout(new FlowLayout());
        comboBox.add(new JLabel("State of the device:"));
        comboBox.add(jComboBox);
        add(comboBox);

        JPanel textField = new JPanel();
        textField.setLayout(new FlowLayout());
        textField.add(new JLabel("Device number: "));
        textField.add(jTextField);
        add(textField);
    }
}
