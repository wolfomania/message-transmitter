package graphic;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class StationPanelTemplate extends JPanel{
        private static int stationCounter = 0;

        protected final int stationIndex;


        public StationPanelTemplate() {
            stationIndex = ++stationCounter;

        }
}
