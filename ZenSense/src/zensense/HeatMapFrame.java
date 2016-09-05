package zensense;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import zensense.ZenSense;
import static zensense.ZenSense.GRIDSIZE;
import static zensense.ZenSense.RIPEVOLTAGE;

class HeatMapFrame extends JFrame
{
    static HeatMap panel;
    public static GridMap gridMap;
    public static SensorBars batteryBars;
    public static SensorBars ripenessBars;

    public HeatMapFrame() throws Exception
    {

        super("ZenSense");
        double[][] data = ZenSense.generateHeatMapData(0);
        
        
        boolean useGraphicsYAxis = true;

        // you can use a pre-defined gradient:

        panel = new HeatMap(data, useGraphicsYAxis, Gradient.GRADIENT_BLUE_TO_RED);

        // or you can also make a custom gradient:

        Color[] gradientColors = new Color[]{Color.yellow,Color.green,Color.blue};
        Color[] customGradient = Gradient.createMultiGradient(gradientColors, 500);
        panel.updateGradient(customGradient);
        
        // set miscellaneous settings
        boolean drawTitles = false;
        panel.setDrawLegend(drawTitles);

        panel.setTitle("Height (m)");
        panel.setDrawTitle(drawTitles);

        panel.setXAxisTitle("X-Distance (m)");
        panel.setDrawXAxisTitle(drawTitles);

        panel.setYAxisTitle("Y-Distance (m)");
        panel.setDrawYAxisTitle(drawTitles);

        panel.setCoordinateBounds(0, 6.28, 0, 6.28);

        panel.setDrawXTicks(drawTitles);
        panel.setDrawYTicks(drawTitles);
        Control control = new Control();
        gridMap = new GridMap();
        batteryBars = new SensorBars(2,"Battery",100);
        ripenessBars = new SensorBars(1,"Ripeness",RIPEVOLTAGE);
        JSplitPane splitPaneMain = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                true, panel, gridMap);
        JSplitPane barCharts = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                true, ripenessBars, batteryBars);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                true, splitPaneMain, barCharts);
        JSplitPane splitPaneControl = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                true, splitPane2, control);
        barCharts.setResizeWeight(0.5);
        splitPane2.setResizeWeight(0.8);
        splitPaneMain.setResizeWeight(0.5);
        splitPaneControl.setResizeWeight(0.8);
       
        getContentPane().add(splitPaneControl);
        
        
    }
    

    private static void createAndShowGUI() throws Exception
    {

        HeatMapFrame hmf = new HeatMapFrame();
        hmf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hmf.setSize(GRIDSIZE,GRIDSIZE);
        hmf.setVisible(true);

    }
    

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    createAndShowGUI();
                    
                }
                catch (Exception e)
                {
                    System.err.println(e);
                    e.printStackTrace();
                }
            }
        });
    }
}