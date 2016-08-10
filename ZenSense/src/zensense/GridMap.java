
package zensense;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static zensense.Control.HISTORICINDEX;
import static zensense.ZenSense.NUMSENSORS;


public class GridMap extends JPanel {
    public double mouseX;
    public double mouseY;
    public double WIDTH;
    public double HEIGHT;
    public GridMap() throws IOException{
        

        
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX=e.getX()*100/WIDTH;
                mouseY=e.getY()*100/HEIGHT;
                updateSensorData();
            }
        });
        this.setVisible(true);
        
    }
    
    public void updateSensorData(){
        for(int i = 0;i<ZenSense.NUMSENSORS;i++){
            if(Math.abs(Double.parseDouble(HeatMap.sensorData.get(HeatMap.sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[3])-mouseX)<=21&&(Math.abs(Double.parseDouble(HeatMap.sensorData.get(HeatMap.sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[4])-mouseY)<=21)){
                Control.displaySensor(HeatMap.sensorData.get(HeatMap.sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX));
                break;
            }
        }
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        try {
            BufferedImage image = ImageIO.read(new File("field.png"));
            g.drawImage(image, 0, 0,getWidth(), getHeight(), this);
        } catch (IOException ex) {
            Logger.getLogger(GridMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        WIDTH = this.getWidth();
        HEIGHT = this.getHeight();
        
        ArrayList<String[]> sensorData = new ArrayList<String[]>();
        try {
            sensorData = ZenSense.readFile();
        } catch (IOException ex) {
            Logger.getLogger(HeatMap.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Time, Voltage, Battery, Xpos%, Ypos%, SensorID

        for(int i = 0;i<ZenSense.NUMSENSORS;i++){            
            g.setColor(getColor(0,ZenSense.RIPEVOLTAGE,Double.parseDouble(sensorData.get(sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[1])));
            g.fillRect((int)(this.getWidth()*Double.parseDouble(sensorData.get(sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[3])/100)-this.getWidth()/(ZenSense.NUMHORIZONTAL*2), (int)(this.getHeight()*Double.parseDouble(sensorData.get(sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[4])/100)-this.getHeight()/(ZenSense.NUMVERTICAL*2), this.getWidth()/ZenSense.NUMHORIZONTAL, this.getHeight()/ZenSense.NUMVERTICAL);
        }
        
    }
    public Color getColor(double minimum, double maximum, double value){
        
        int b = 0;
        int r = 0;
        int g = 0;
        if(value/ZenSense.RIPEVOLTAGE>=0.5){
            g = 255;
            r = (int)(255*(1-value/ZenSense.RIPEVOLTAGE));
        }else{
            r = 255;
            g = (int)(255*(value/ZenSense.RIPEVOLTAGE));
        }
       
        return (new Color(r,g,b,127));
    }
}
