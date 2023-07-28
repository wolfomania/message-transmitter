package graphic;

import events.IntelToGraphic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyGraphics extends JFrame {

    private IntelToGraphic intel;
    LeftPanel leftPanel;
    MiddlePanel middlePanel;
    RightPanel rightPanel;
    public MyGraphics() {
        setTitle("Message Transmitter");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if(intel != null)
                    intel.saveAll();
                else System.err.println("Can't save info. \nLogic has not been connected");
                System.out.println("Closing the window");
            }
        });

        SwingUtilities.invokeLater(() -> {

            leftPanel = new LeftPanel();
            getContentPane().add(leftPanel, BorderLayout.WEST);

            middlePanel = new MiddlePanel();
            getContentPane().add(middlePanel, BorderLayout.CENTER);

            rightPanel = new RightPanel();
            getContentPane().add(rightPanel, BorderLayout.EAST);

            setVisible(true);

        });

    }

    public void setIntel(IntelToGraphic intel) {
        this.intel = intel;
        SwingUtilities.invokeLater(() -> {
            leftPanel.setLeftPanelIntel(intel);
            middlePanel.setMiddleLayerIntel(intel);
            rightPanel.setRightPanelIntel(intel);
        });
    }
    public static void showError(String message, String title, int displayDuration) {

        final JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
        final JDialog dialog = new JDialog();
        dialog.setTitle(title);
        dialog.setContentPane(optionPane);

        dialog.setUndecorated(true);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(displayDuration); // Sleep for the specified duration
                dialog.dispose(); // Close the dialog after the delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
