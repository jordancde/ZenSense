package zensense;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Control extends JPanel
{
  static JFrame frame;
  static int HISTORICINDEX = 0; 
  static JLabel datelabel;
  public static String[] selectedSensorData;
  static JLabel sensorIDLabel;
  static JLabel sensorVoltageLabel;
  static JLabel sensorBatteryLabel;
  
  
  
  
  public Control()
  {
    //frame = new JFrame("Control Panel");

    // create our jbutton
    JButton previous = new JButton("Previous");
    JButton next = new JButton("Next");
    datelabel = new JLabel(ZenSense.dataDate.toString());
    sensorIDLabel = new JLabel("Click a sensor for information");
    sensorVoltageLabel = new JLabel("");
    sensorBatteryLabel = new JLabel("");
   
    
    // add the listener to the jbutton to handle the "pressed" event
    previous.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
          HISTORICINDEX++;
          try {
              refreshAll();
          } catch (Exception ex) {
              JOptionPane.showMessageDialog(Control.this, "no data for this time period");
              HISTORICINDEX--;
              try {
                  refreshAll();
              } catch (Exception ex1) {
                  Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex1);
              }
          }
      }
    });
    next.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
          HISTORICINDEX--;
          try {
              refreshAll();
          } catch (Exception ex) {
              
              JOptionPane.showMessageDialog(Control.this, "no data for this time period");
              HISTORICINDEX++;
              try {
                  refreshAll();
              } catch (Exception ex1) {
                  Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex1);
              }
          }
      }
    });
    // put the button on the frame
    this.setLayout(new FlowLayout());
    this.add(previous);
    this.add(next);
    this.add(datelabel);
    this.add(sensorIDLabel);
    this.add(sensorVoltageLabel);
    this.add(sensorBatteryLabel);

    // set up the jframe, then display it
    //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setPreferredSize(new Dimension(300, 200));
    //this.pack();
    //this.setLocationRelativeTo(null);
    this.setVisible(true);
  }
  
  static void refreshAll() throws IOException{
      datelabel.setText("Loading...");
      HeatMapFrame.panel.updateData(ZenSense.generateHeatMapData(HISTORICINDEX), true);
      SensorLevels.refreshData(HISTORICINDEX);
      datelabel.setText(ZenSense.dataDate.toString());
      sensorIDLabel.setText("Click a sensor for information");
      sensorVoltageLabel.setText("");
      sensorBatteryLabel.setText("");        
  }
  
  //Time, Voltage, Battery, Xpos%, Ypos%, SensorID
  static void displaySensor(String[] sensorData){
      sensorIDLabel.setText("Sensor ID: #"+(int)Double.parseDouble(sensorData[5]));
      sensorVoltageLabel.setText("Voltage: "+Math.round(Double.parseDouble(sensorData[1])*1000)/1000+"/"+ZenSense.RIPEVOLTAGE+"V");
      sensorBatteryLabel.setText("Battery Level: "+sensorData[2]+"%");
  }
  
  
}