package graphic;

import events.IntelToGraphic;
import events.IntelToReceivers;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VirtualReceivingDevicePanel extends JPanel implements IntelToReceivers {

    private JButton jButton;
    private JLabel jLabel;
    private JCheckBox jCheckBox;
    private volatile boolean isChecked;

    private volatile boolean active;

    private int numOfMessagesUpdate;

    private static int deviceCounter = 0;

    private int deviceIndex;

    private Thread sizeUpdater;

    private final IntelToGraphic receivingDeviceIntel;
//    private static List<graphic.VirtualReceivingDevicePanel> deviceList = new ArrayList<>();
    private List<String> messages;
    public VirtualReceivingDevicePanel(IntelToGraphic intel) {
        active = true;
        numOfMessagesUpdate = 0;
        receivingDeviceIntel = intel;
        deviceIndex = ++deviceCounter;
        messages = new ArrayList<>();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 100));
        setMaximumSize(new Dimension(300,200));
        setBorder(new EtchedBorder());

        jButton = new JButton("Terminate");
        jButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton.addActionListener(e -> {
            Container parent = getParent();
            receivingDeviceIntel.removeReceivingDevice(deviceIndex);
            parent.remove(VirtualReceivingDevicePanel.this);
            isChecked = false;
            active = false;
//            deviceList.remove(graphic.VirtualReceivingDevicePanel.this);
            parent.revalidate();
            parent.repaint();
        });

        jLabel = new JLabel("" + messages.size());
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        isChecked = false;
        sizeUpdater = new Thread(() -> {
            while(active) {
                if(isChecked) {
                    numOfMessagesUpdate = receivingDeviceIntel.getNumOfMessages(deviceIndex);
                }
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        jCheckBox = new JCheckBox();
        jCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        jCheckBox.addItemListener(e -> {
            isChecked = jCheckBox.isSelected();
        });

        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());
        button.add(new JLabel("Terminate the device:"));
        button.add(jButton);
        add(button);
//        add(jButton);

        JPanel label = new JPanel();
        label.setLayout(new FlowLayout());
        label.add(new JLabel("Number of messages: "));
        label.add(jLabel);
        add(label);
//        add(jLabel);

        JPanel checkBox = new JPanel();
        checkBox.setLayout(new FlowLayout());
        checkBox.add(new JLabel("Clear history every ten seconds"));
        checkBox.add(jCheckBox);
        add(checkBox);

        sizeUpdater.start();
    }

    @Override
    public void setNumOfMessage(int num) {
        int finalResult = num - numOfMessagesUpdate;
        jLabel.setText("" + finalResult);
    }


//    public static List<graphic.VirtualReceivingDevicePanel> getDeviceList() {
//        return deviceList;
//    }

}
