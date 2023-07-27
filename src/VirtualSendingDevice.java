import java.io.*;
import java.sql.SQLOutput;
import java.util.HashMap;

public class VirtualSendingDevice {

    private static HashMap<Integer, VirtualSendingDevice> listOfSendingDevices = new HashMap<>();

    private static StationLayer firstLayer;

    private boolean isActive;

    private int frequency;

    private final String message;

    private Sender sender;

    private final int deviceIndex;

    private static int countOfDevices = 0;

    private int countOfSentMessages;


    public VirtualSendingDevice(String message, int frequency) {
        deviceIndex = ++countOfDevices;
        countOfSentMessages = 0;
        listOfSendingDevices.put(deviceIndex, this);
        this.frequency = frequency;
        isActive = true;
        this.message = message;
        sender = new Sender();
        sender.start();
    }

    private class Sender extends Thread{

        @Override
        public void run() {
            while(isActive){
                if(VirtualReceivingDevice.getRandomIndex() != 0) {
                    try {
                        Thread.sleep(1000 / frequency);
                        int destinationIndex = VirtualReceivingDevice.getRandomIndex();
                        String encodedMessage = new PDU(message, deviceIndex ,destinationIndex).getEncodedSMS();
                        firstLayer.getMessage(encodedMessage);
                        countOfSentMessages++;

                    } catch (InterruptedException e) {
                    }
                } else System.err.println("No rec dev");
            }
        }
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void startSending() {
        isActive = true;
        sender = new Sender();
        sender.start();
    }

    public void stopSending() {
        isActive = false;
    }

    public static void setFirstLayer(StationLayer firstLayer) {
        VirtualSendingDevice.firstLayer = firstLayer;
    }

    public static void removeSendingDevice(int deviceIndex) {
        if(listOfSendingDevices.containsKey(deviceIndex)) {
            listOfSendingDevices.get(deviceIndex).stopSending();
            listOfSendingDevices.remove(deviceIndex);
        }
        else System.err.println("EEERRROOORRR no such device");
    }

    public static void startSending(int deviceIndex) {
        listOfSendingDevices.get(deviceIndex).startSending();
    }

    public static void stopSending(int deviceIndex) {
        listOfSendingDevices.get(deviceIndex).stopSending();
    }

    public static void setFrequency(int deviceIndex, int frequency) {
        listOfSendingDevices.get(deviceIndex).setFrequency(frequency);
    }

    public static void save() {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("infoAboutSentMessages.bin"))){
            for(VirtualSendingDevice s : listOfSendingDevices.values()) {
                dataOutputStream.writeInt(s.countOfSentMessages);
                String converted = PDU.convert(s.message);
                for(int i = 0, k = 2; k <= converted.length(); i+=2, k+=2) {
                    dataOutputStream.write(Integer.parseInt(converted.substring(i, k), 16));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
