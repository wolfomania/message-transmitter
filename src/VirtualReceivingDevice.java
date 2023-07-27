import javax.swing.*;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VirtualReceivingDevice {

    private static HashMap<Integer, VirtualReceivingDevice> virtualReceivingDeviceList = new HashMap<>();
    private List<String> messages;

    static int receiverCounter = 0;

    private final int receiverIndex;

    private final IntelToReceivers intelToReceivers;

    public VirtualReceivingDevice(IntelToReceivers intel) {
        intelToReceivers = intel;
        receiverIndex = ++receiverCounter;
        virtualReceivingDeviceList.put(receiverIndex, this);
        messages = new ArrayList<>();
    }

    public VirtualReceivingDevice() {
        intelToReceivers = null;
        receiverIndex = ++receiverCounter;
        virtualReceivingDeviceList.put(receiverIndex, this);
        messages = new ArrayList<>();
    }



    public static int getRandomIndex() {
        if(virtualReceivingDeviceList.isEmpty())
            return 0;

        int rand = (int)(Math.random() * virtualReceivingDeviceList.size()) + 1;
        int index = 1;
        for(VirtualReceivingDevice v : virtualReceivingDeviceList.values()) {
            if(index == rand)
                return v.receiverIndex;
            index++;
        }

        return 0;
    }

    public static void sendMessageTo(String message, int index) {

        if(virtualReceivingDeviceList.get(index) == null) {
            System.err.println("No receiving device with number: " + index);

            new Thread(() -> {
                try {
                    JOptionPane optionPane = new JOptionPane("Device with index " + index + " was not found", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                    JDialog dialog = new JDialog();
                    dialog.setTitle("");
                    dialog.setContentPane(optionPane);

                    dialog.setUndecorated(true);
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    Thread.sleep(2000); // Sleep for the specified duration
                    dialog.dispose(); // Close the dialog after the delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            virtualReceivingDeviceList.get(index).messages.add(message);
            if(virtualReceivingDeviceList.get(index).intelToReceivers != null)
                virtualReceivingDeviceList.get(index).intelToReceivers.setNumOfMessage(virtualReceivingDeviceList.get(index).messages.size());
            System.err.println("successfully delivered");
        }
    }

    public static void deleteReceivingDevice(int deviceIndex) {
        virtualReceivingDeviceList.remove(deviceIndex);
    }

    public static int getNumOfMessagesIn(int receiverIndex) {
        return virtualReceivingDeviceList.get(receiverIndex).messages.size();
    }

//    public static void save() {
//        int numOfAllMessages = calculateSumOfAllMessages();
//
//
//        try(DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("messages.bin"))) {
//            outputStream.writeInt(numOfAllMessages);
//
//        }catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static int calculateSumOfAllMessages() {
//        int sum = 0;
//        for(VirtualReceivingDevice v : virtualReceivingDeviceList.values()) {
//            sum += v.messages.size();
//        }
//        return sum;
//    }
//
//    private static writeMessage() {
//
//    }
//
//    private static ArrayList<String> convertIntoList() {
//        ArrayList<String> all = new ArrayList<>();
//        for(VirtualReceivingDevice v : virtualReceivingDeviceList.values()) {
//            all.addAll(v.messages);
//        }
//        virtualReceivingDeviceList.clear();
//        return all;
//    }
}
