public interface IntelToGraphic {

    void addVirtualSendingDevice(String message, int frequency);

    void removeVirtualSendingDevice(int deviceIndex);

    void startSendingMessages(int deviceIndex);

    void stopSendingMessages(int deviceIndex);

    void setSendingFrequency(int deviceIndex, int frequency);

    void addReceivingDevice(IntelToReceivers intel);

    void removeReceivingDevice(int deviceIndex);

    int getNumOfMessages(int receiverIndex);

    void saveAll();

    void addStationLayer();

    void removeStationLayer();

    StationLayer getFirstLayer();
}
