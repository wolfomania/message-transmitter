package logic;

import events.IntelToGraphic;
import events.IntelToReceivers;

public class MyLogic implements IntelToGraphic {

//    private List<logic.VirtualSendingDevice> listOfSendingDevices;

    private StationLayer firstLayer;

//    private events.IntelToLayers intelToLayers;
    public MyLogic() {

//        this.listOfSendingDevices = new ArrayList<>();
        firstLayer = new StationLayer();
        StationLayer middleLayer = new StationLayer();
        middleLayer.setNextStationLayer(new StationLayer()); //Adding last BTS layer
        firstLayer.setNextStationLayer(middleLayer);
        VirtualSendingDevice.setFirstLayer(firstLayer);

    }

    @Override
    public void addVirtualSendingDevice(String message, int frequency) {
        new VirtualSendingDevice(message, frequency);
    }

    @Override
    public void removeVirtualSendingDevice(int deviceIndex) {
        VirtualSendingDevice.removeSendingDevice(deviceIndex);
    }

    @Override
    public void startSendingMessages(int deviceIndex) {
        VirtualSendingDevice.startSending(deviceIndex);
    }

    @Override
    public void stopSendingMessages(int deviceIndex) {
        VirtualSendingDevice.stopSending(deviceIndex);
    }

    @Override
    public void setSendingFrequency(int deviceIndex, int frequency) {
        VirtualSendingDevice.setFrequency(deviceIndex, frequency);
    }

    @Override
    public void addReceivingDevice(IntelToReceivers intel) {
        new VirtualReceivingDevice(intel);
    }

    @Override
    public void removeReceivingDevice(int deviceIndex) {
        VirtualReceivingDevice.deleteReceivingDevice(deviceIndex);
    }

    @Override
    public int getNumOfMessages(int receiverIndex) {
        return VirtualReceivingDevice.getNumOfMessagesIn(receiverIndex);
    }

    @Override
    public void saveAll() {
        VirtualSendingDevice.save();
    }

    @Override
    public void addStationLayer() {
        firstLayer.addNextStationLayer(new StationLayer());
    }

    @Override
    public void removeStationLayer() {
        StationLayer iterate = firstLayer;
        int count = 3;
        while(iterate.getNextStationLayer().getNextStationLayer().getNextStationLayer() != null){
            iterate = iterate.getNextStationLayer();
            count++;
        }
        if(count > 3) {
            StationLayer temp = iterate.getNextStationLayer();
            iterate.setNextStationLayer(iterate.getNextStationLayer().getNextStationLayer());
            temp.sendEverythingToNext();
        }
    }

    @Override
    public StationLayer getFirstLayer() {
        return firstLayer;
    }
//
//    public void setIntelToLayers(events.IntelToLayers intelToLayers) {
//        this.intelToLayers = intelToLayers;
//    }

    //  Things to implement in Intel:
//
//    public void addLayer() {
//
//    }
//
//    public void terminateLayer() {
//
//    }
//    public void terminateDevice() {
//        this.stopSending();
//        listOfSendingDevices.remove(this.deviceIndex);
//    }
}
