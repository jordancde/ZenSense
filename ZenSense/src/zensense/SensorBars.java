/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zensense;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import static zensense.Control.HISTORICINDEX;
import static zensense.ZenSense.NUMSENSORS;
import static zensense.ZenSense.selectedSensor;

public class SensorBars extends JPanel {
  private double[] values;

  private String[] names;

  private String title;
  
  public ArrayList<String[]> sensorData;
  
  public int dataIndex;
  public int maxValue;
  
  public SensorBars(int dataToDisplay, String title, int maxValue) {
     dataIndex = dataToDisplay;
   
    sensorData = ZenSense.fileData;
    //Time, Voltage, Battery, Xpos%, Ypos%, SensorID
    names = new String[NUMSENSORS];
    values = new double[NUMSENSORS];
    this.title = title;
    this.maxValue = maxValue;
    
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    sensorData = ZenSense.fileData;
    for(int i = 0;i<ZenSense.NUMSENSORS;i++){            
        names[i] = sensorData.get(sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[5];
        values[i] = Double.parseDouble(sensorData.get(sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[dataIndex]);
    }
    
    if (values == null || values.length == 0)
      return;
    double minValue = 0;
    
    
    Dimension d = getSize();
    int clientWidth = d.width;
    int clientHeight = d.height;
    int barWidth = clientWidth / values.length;

    Font titleFont = new Font("SansSerif", Font.BOLD, 15);
    FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
    Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
    FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

    int titleWidth = titleFontMetrics.stringWidth(title);
    int y = titleFontMetrics.getAscent();
    int x = (clientWidth - titleWidth) / 2;
    g.setFont(titleFont);
    g.drawString(title, x, y);

    int top = titleFontMetrics.getHeight();
    int bottom = labelFontMetrics.getHeight();
    if (maxValue == minValue)
      return;
    double scale = (clientHeight - top - bottom) / (maxValue - minValue);
    y = clientHeight - labelFontMetrics.getDescent();
    g.setFont(labelFont);

    for (int i = 0; i < values.length; i++) {
      int valueX = i * barWidth + 1;
      int valueY = top;
      int height = (int) (values[i] * scale);
      if (values[i] >= 0)
        valueY += (int) ((maxValue - values[i]) * scale);
      else {
        valueY += (int) (maxValue * scale);
        height = -height;
      }
      if(selectedSensor!=null&&Double.parseDouble(sensorData.get(sensorData.size()-i-1-NUMSENSORS*HISTORICINDEX)[5])==Double.parseDouble(selectedSensor[5])){
        g.setColor(new Color(0,153,255));
      }else{
        g.setColor(Color.red);
      }
      
      g.fillRect(valueX, valueY, barWidth - 2, height);
      g.setColor(Color.black);
      g.drawRect(valueX, valueY, barWidth - 2, height);
      int labelWidth = labelFontMetrics.stringWidth(names[i]);
      x = i * barWidth + (barWidth - labelWidth) / 2;
      g.drawString(names[i], x, y);
    }
  }

  
}
