
package zensense;


import java.io.IOException;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import static zensense.ZenSense.NUMSENSORS;

public class SensorLevels extends ApplicationFrame
{
   public static DefaultCategoryDataset chartData;
   public static JFreeChart barChart;
   public static ChartPanel chartPanel;
   public static String applicationTitle = "Sensor Levels";
   public static String chartTitle = "Battery and Ripeness Levels (%)";
   public SensorLevels() throws IOException
   {
      super( applicationTitle );   
      chartData = createDataset();
      barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Category",            
         "Score",            
         chartData,          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
      
   }
   private DefaultCategoryDataset createDataset( ) throws IOException
   {
      ArrayList<String[]> sensorData = ZenSense.readFile();
    
      final String battery = "Battery";        
      final String ripeness = "Ripeness";        
        
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  

      for(int i = 0;i<ZenSense.NUMSENSORS;i++){
          dataset.addValue( Double.parseDouble(sensorData.get(sensorData.size()-i-1)[2]) , "Sensor "+sensorData.get(sensorData.size()-i-1)[5] , battery );        
          dataset.addValue( Double.parseDouble(sensorData.get(sensorData.size()-i-1)[1])/ZenSense.RIPEVOLTAGE*100 , "Sensor "+sensorData.get(sensorData.size()-i-1)[5] , ripeness );        
      }
      
      return dataset; 
   }
   
   public static void refreshData(int historicIndex) throws IOException{
       ArrayList<String[]> sensorData = ZenSense.readFile();
       final String battery = "Battery";        
      final String ripeness = "Ripeness";        
        
      //final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );  
      for(int i = 0;i<ZenSense.NUMSENSORS;i++){
          chartData.addValue( Double.parseDouble(sensorData.get(sensorData.size()-i-1-NUMSENSORS*historicIndex)[2]) , "Sensor "+sensorData.get(sensorData.size()-i-1)[5] , battery );        
          chartData.addValue( Double.parseDouble(sensorData.get(sensorData.size()-i-1-NUMSENSORS*historicIndex)[1])/ZenSense.RIPEVOLTAGE*100 , "Sensor "+sensorData.get(sensorData.size()-i-1)[5] , ripeness );        
      }
   
   }
   
   
   public static void main( String[ ] args ) throws IOException
   {
      SensorLevels chart = new SensorLevels();
      chart.pack( );        
      RefineryUtilities.centerFrameOnScreen( chart );        
      chart.setVisible( true );
      
   }
}