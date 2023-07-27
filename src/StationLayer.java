import java.util.HashMap;

public class StationLayer {

    private StationLayer nextStationLayer;

    private HashMap<Integer, StationFunctionality> listOfStations;

    private static int layerCounter = 0;

    private final int layerIndex;


    public StationLayer() {
        layerIndex = ++layerCounter;
        listOfStations = new HashMap<>();
        nextStationLayer = null;
    }

    public StationLayer getNextStationLayer() {
        return nextStationLayer;
    }

    public void addNextStationLayer(StationLayer next) {
        StationFunctionality temp = new ControlStation();
        next.listOfStations.put(temp.getStationIndex(), temp);
        next.nextStationLayer = this.nextStationLayer;
        this.nextStationLayer = next;
    }

    public void setNextStationLayer(StationLayer next) {
        nextStationLayer = next;
    }

    public void sendEverythingToNext() {
        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
        }
        for(StationFunctionality s : listOfStations.values()) {
            for(int i = 0; i < s.messages.length; i++) {
                if(!s.messages[i].equals("")) {
                    s.sendMessage(i);
                }
            }
        }
    }

    /**
     * @return an array where the first element is stationIndex and second is messageIndex
     */
    public synchronized int[] getAddressToStationFreeSpace(){

        int[] finalAddress = null;
        StationFunctionality temp = null;

        for (StationFunctionality s : listOfStations.values()) {
            int[] tempIndex = s.getFreeSpaceIndex();
            if(tempIndex != null && (finalAddress == null || finalAddress[0] < tempIndex[0])) {
                finalAddress = tempIndex;
                temp = s;
            }
        }

        if(finalAddress != null) {
            listOfStations.get(temp.getStationIndex()).messages[finalAddress[1]] = "EMPTY";
            return new int[]{temp.getStationIndex(), finalAddress[1]};
        }

        temp = new BaseStation();
        if(layerIndex != 1 && nextStationLayer != null) {
            temp = new ControlStation();
        }

        listOfStations.put(temp.getStationIndex(), temp);
        temp.messages[0] = "EMPTY";
        return new int[]{temp.getStationIndex(), 0};

    }

    private int getNumOfElemToTheEnd() {
        StationLayer temp = this;
        int count = 0;
        while (temp.getNextStationLayer() != null) {
            count++;
        }
        return count;
    }

    /**
     * Generally, auxiliary function that check existence of the station <p>
     *     Recommended to use after <code>getAddressToStationFreeSpace()</code>
     * @param stationIndex index of the station
     * @return <code>true</code> if station with index <code>stationIndex</code> exist <p>
     *     <code>false</code> otherwise
     * @see StationLayer#getAddressToStationFreeSpace()
     */
//    public boolean isStationExist(int stationIndex) {
//        return listOfStations.get(stationIndex) != null;
//    }

    /**
     * Method simply adding message to specified location, nothing more. <p>
     * Any possible errors should be handled before the invoking.
     * @param index station index
     * @param place index under witch the message will be located in the array
     * @param message the message itself
     */
    public void addMessageTo(int index, int place, String message){
        listOfStations.get(index).messages[place] = message;
    }

    public void getMessage(String message) {
        int[] tempAddress = getAddressToStationFreeSpace();
        addMessageTo(tempAddress[0], tempAddress[1], message);
        listOfStations.get(tempAddress[0]).sendMessage(tempAddress[1]);
    }

    private class BaseStation extends StationFunctionality{

        private final int frequency;
        public BaseStation() {
            super();
            frequency = 3 * 1000;

        }

        @Override
        public void sendMessage(int index) {
            new Thread(new BaseRunner(index)).start();
        }

        private class BaseRunner implements Runnable {

            private final int messageIndex;

            private final boolean isLastLayer;

            /**
             * @param messageIndex index of the message you want to send
             */
            public BaseRunner(int messageIndex) {
                this.messageIndex = messageIndex;
                this.isLastLayer = nextStationLayer == null;
            }

            @Override
            public void run() {
                try {
                    Thread.sleep(frequency);
                } catch (InterruptedException ignore) { }

                if(!isLastLayer) {
                    System.err.println("Message sent to next contrl dev:" + nextStationLayer.layerIndex);

                    int[] tempAddress = nextStationLayer.getAddressToStationFreeSpace();
                    nextStationLayer.addMessageTo(tempAddress[0], tempAddress[1], nullifyMessage(messageIndex));
                    nextStationLayer.listOfStations.get(tempAddress[0]).sendMessage(tempAddress[1]);
                } else {
                    int destination = Integer.parseInt(messages[messageIndex].substring(2, 4), 16);
                    VirtualReceivingDevice.sendMessageTo(listOfStations.get(stationIndex).nullifyMessage(messageIndex), destination);
                }

            }
        }

    }
    private class ControlStation extends StationFunctionality{

        public ControlStation() {
            super();
        }

        @Override
        public void sendMessage(int index) {
            new Thread(new ControlRunner(index)).start();
        }

        private class ControlRunner implements Runnable {

            private final int messageIndex;

            /**
             * @param messageIndex index of the message you want to send
             */
            public ControlRunner(int messageIndex) {
                this.messageIndex = messageIndex;
            }

            @Override
            public void run() {
                System.err.println("message at controll station");
                try {
                    Thread.sleep((5 + (int)(Math.random() * 10)) * 1000);
                } catch (InterruptedException ignore) { }

                int[] tempAddress = nextStationLayer.getAddressToStationFreeSpace();
                System.err.println("Message sent to next base dev:" + nextStationLayer.layerIndex);
                nextStationLayer.addMessageTo(tempAddress[0], tempAddress[1], nullifyMessage(messageIndex));
                nextStationLayer.listOfStations.get(tempAddress[0]).sendMessage(tempAddress[1]);
            }
        }

    }

//    private class MyRunner implements Runnable {
//
//        private final int frequency;
//
//        private final int stationIndex;
//
//        private final int messageIndex;
//
//        private final boolean isLastLayer;
////
////        public MyRunner(int stationIndex, int messageIndex, int frequency) {
////            this.stationIndex = stationIndex;
////            this.messageIndex = messageIndex;
////            this.frequency = frequency;
////        }
//
//        /**
//         * @param stationIndex station from which message will be sent
//         * @param messageIndex index of the message you want to send
//         */
//        public MyRunner(int stationIndex, int messageIndex) {
//            this.stationIndex = stationIndex;
//            this.messageIndex = messageIndex;
//            this.isLastLayer = nextStationLayer == null;
//            if(layerIndex == 1 || nextStationLayer == null) {
//                frequency = 3 * 1000;
//            } else frequency = (5 + (int)(Math.random() * 10)) * 1000;
//        }
//
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(frequency);
//            } catch (InterruptedException ignore) { }
//
//            if(isLastLayer) {
////
//            } else {
//                int[] tempAddress = nextStationLayer.getAddressToStationFreeSpace();
//                nextStationLayer.addMessageTo(tempAddress[0], tempAddress[1], listOfStations.get(stationIndex).nullifyMessage(messageIndex));
//
//            }
//        }
//    }

}
