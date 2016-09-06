package zensense;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static zensense.ZenSense.NUMSENSORS;
import static zensense.ZenSense.RIPEDAYS;
import static zensense.ZenSense.RIPEVOLTAGE;

public class Control extends JPanel
{
  static JFrame frame;
  static int HISTORICINDEX = 0; 
  static JLabel datelabel;
  public static String[] selectedSensorData;
  static JLabel sensorIDLabel;
  static JLabel sensorVoltageLabel;
  static JLabel sensorBatteryLabel;
  static JLabel sensorDaysUntil;
  
  
  
  
  public Control()
  {
    //frame = new JFrame("Control Panel");

    // create our jbutton
    JButton previous = new JButton("Previous");
    previous.setAlignmentX(Component.RIGHT_ALIGNMENT);
    JButton next = new JButton("Next");
    JButton pullDataButton = new JButton("Refresh");
    JButton reset = new JButton("Reset");
    reset.setAlignmentX(Component.LEFT_ALIGNMENT);
    reset.setAlignmentY(Component.TOP_ALIGNMENT);
    next.setAlignmentX(Component.LEFT_ALIGNMENT);
    next.setAlignmentY(Component.TOP_ALIGNMENT);
    datelabel = new JLabel(ZenSense.dataDate.toString());
    datelabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    sensorIDLabel = new JLabel("Click a sensor for information");
    sensorIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    sensorVoltageLabel = new JLabel("");
    sensorVoltageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    sensorBatteryLabel = new JLabel("");
    sensorBatteryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    sensorDaysUntil = new JLabel("");
    sensorDaysUntil.setAlignmentX(Component.CENTER_ALIGNMENT);
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
    pullDataButton.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
          HISTORICINDEX = 0;
          try {
              ZenSense.refreshData();
              refreshAll();
              
          } catch (Exception ex) {
              try {
                  ZenSense.refreshData();
                  refreshAll();
              } catch (Exception ex1) {
                  Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex1);
              }
          }
      }
    });
    reset.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
          HISTORICINDEX = 0;
          try {
              ZenSense.reset();
              ZenSense.refreshData();
              refreshAll();
              
          } catch (Exception ex) {
              try {
                  ZenSense.reset();
                  ZenSense.refreshData();
                  refreshAll();
              } catch (Exception ex1) {
                  Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex1);
              }
          }
      }
    });
    // put the button on the frame
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
    JPanel buttons = new JPanel();
    buttons.setLayout(new FlowLayout(FlowLayout.CENTER));
    
    buttons.add(previous);
    buttons.add(pullDataButton);
    buttons.add(reset);
    buttons.add(next);
    
    this.add(datelabel);
    this.add(sensorIDLabel);
    this.add(sensorVoltageLabel);
    this.add(sensorBatteryLabel);
    this.add(sensorBatteryLabel);
    this.add(sensorDaysUntil);
    this.add(buttons);
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
      datelabel.setText(ZenSense.dataDate.toString());
      HeatMapFrame.gridMap.repaint();
      HeatMapFrame.batteryBars.repaint();
      HeatMapFrame.ripenessBars.repaint();
      ZenSense.selectedSensor = null;
      sensorIDLabel.setText("Click a sensor for information");
      sensorVoltageLabel.setText("");
      sensorBatteryLabel.setText(""); 
      sensorDaysUntil.setText("");  
  }
  
  //Time, Voltage, Battery, Xpos%, Ypos%, SensorID
  static void displaySensor(String[] sensorData){
      sensorIDLabel.setText("Sensor ID: #"+(int)Double.parseDouble(sensorData[5]));
      sensorVoltageLabel.setText("Voltage: "+Math.round(Double.parseDouble(sensorData[1])*1000)/1000+"/"+ZenSense.RIPEVOLTAGE+"V");
      sensorBatteryLabel.setText("Battery Level: "+Math.round(Double.parseDouble(sensorData[2])*100)/100+"%");
      sensorDaysUntil.setText((RIPEDAYS-Math.round(RIPEDAYS * Double.parseDouble(sensorData[1]) / RIPEVOLTAGE)+" Days Left"));
 
  }
  
  
}