package zensense;

import javax.swing.*;
import java.awt.*;
import zensense.ZenSense;

class HeatMapFrame extends JFrame
{
    static HeatMap panel;

    public HeatMapFrame() throws Exception
    {

        super("Heat Map");
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

        this.getContentPane().add(panel);
    }

    // this function will be run from the EDT

    private static void createAndShowGUI() throws Exception
    {

        HeatMapFrame hmf = new HeatMapFrame();
        hmf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hmf.setSize(500,500);
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