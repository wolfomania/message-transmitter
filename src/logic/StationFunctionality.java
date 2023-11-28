package logic;

import java.util.Arrays;

public abstract class StationFunctionality {

    protected String[] messages;

    private boolean isActive;

    private static int stationCounter = 0;

    protected final int stationIndex;
    protected StationFunctionality() {
        isActive = true;
        stationIndex = ++stationCounter;
        messages = new String[5];
        Arrays.fill(messages, "");

    }
//
//    public void sendMessageTo(logic.StationLayer layer, String message) {
//        int[] tempAddress = layer.getAddressToStationFreeSpace();
//        layer.addMessageTo(tempAddress[0], tempAddress[1], message);
//    }
    public abstract void sendMessage(int index);

    public int getStationIndex() {
        return stationIndex;
    }

    /**
     * This method nullify a message under the specified index and returns it
     * @param messageIndex
     * @return returns a String from the messages array under the index messageIndex
     */
    public String nullifyMessage(int messageIndex) {
        String temp = new String(messages[messageIndex]);
        messages[messageIndex] = "";
        return temp;
    }

    public int[] getFreeSpaceIndex(){ //returns none if there are none

        int index = 0;
        int count = 0;
        for(int i = 0; i < messages.length; i++) {
            if (messages[i].equals("")) {
                count++;
                index = i;
            }
        }
        if(count == 0)
            return null;
        return new int[]{count, index};

    }


}
