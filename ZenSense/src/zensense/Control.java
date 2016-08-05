package zensense;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Control
{
  static JFrame frame;
  static int HISTORICINDEX = 0; 
  static JLabel datelabel;
  public static void main(String[] args)
  {
    // schedule this for the event dispatch thread (edt)
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        displayJFrame();
      }
    });
  }

  static void displayJFrame()
  {
    frame = new JFrame("Control Panel");

    // create our jbutton
    JButton previous = new JButton("Previous");
    JButton next = new JButton("Next");
    datelabel = new JLabel(ZenSense.dataDate.toString());
    
    // add the listener to the jbutton to handle the "pressed" event
    previous.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
          HISTORICINDEX++;
          try {
              datelabel.setText("Loading...");
              HeatMapFrame.panel.updateData(ZenSense.generateHeatMapData(HISTORICINDEX), true);
              SensorLevels.refreshData(HISTORICINDEX);
              datelabel.setText(ZenSense.dataDate.toString());
          } catch (Exception ex) {
              JOptionPane.showMessageDialog(frame, "no data for this time period");
              HISTORICINDEX--;
              try {
                  HeatMapFrame.panel.updateData(ZenSense.generateHeatMapData(HISTORICINDEX), true);
                  SensorLevels.refreshData(HISTORICINDEX);
                  datelabel.setText(ZenSense.dataDate.toString());
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
              datelabel.setText("Loading...");
              HeatMapFrame.panel.updateData(ZenSense.generateHeatMapData(HISTORICINDEX), true);
              SensorLevels.refreshData(HISTORICINDEX);
              datelabel.setText(ZenSense.dataDate.toString());
          } catch (Exception ex) {
              JOptionPane.showMessageDialog(frame, "no data for this time period");
              HISTORICINDEX++;
              try {
                  HeatMapFrame.panel.updateData(ZenSense.generateHeatMapData(HISTORICINDEX), true);
                  SensorLevels.refreshData(HISTORICINDEX);
                  datelabel.setText(ZenSense.dataDate.toString());
              } catch (Exception ex1) {
                  Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex1);
              }
          }
      }
    });
    // put the button on the frame
    frame.getContentPane().setLayout(new FlowLayout());
    frame.add(previous);
    frame.add(next);
    frame.add(datelabel);

    // set up the jframe, then display it
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(300, 200));
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  
  
}