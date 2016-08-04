package zensense;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import static zensense.Control.HISTORICINDEX;
import zensense.ZenSense;
import static zensense.ZenSense.NUMSENSORS;

public class SensorMap extends JFrame {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public SensorMap() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void paint(Graphics g) {
        super.paint(g);

        ArrayList<String[]> sensorData = new ArrayList<String[]>();
        try {
            sensorData = ZenSense.readFile();
        } catch (Exception ex) {
            Logger.getLogger(SensorMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.setColor(Color.red);

        for(int i = 0;i<NUMSENSORS;i++){
            g.fillOval((int) (WIDTH*Double.parseDouble(sensorData.get(sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[3])/100), (int) (HEIGHT*Double.parseDouble(sensorData.get(sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[4])/100), 20, 20);
        }


    }

    public void refresh(){
        this.invalidate();
        this.validate();
        this.repaint();
    }
}





