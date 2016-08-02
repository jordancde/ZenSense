
package zensense;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class SensorLevels extends ApplicationFrame
{
   public SensorLevels( String applicationTitle , String chartTitle )
   {
      super( applicationTitle );        
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Category",            
         "Score",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
   }
   private CategoryDataset createDataset( )
   {
      final String sensor1 = "Sensor 1";        
      final String sensor2 = "Sensor 2";        
      final String sensor3 = "Sensor 3"; 
      
      final String battery = "Battery";        
      final String ripeness = "Ripeness";        
        
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  

      dataset.addValue( 1.0 , sensor1 , battery );        
      dataset.addValue( 5.0 , sensor1 , ripeness ); 

      dataset.addValue( 5.0 , sensor2 , battery );        
      dataset.addValue( 10.0 , sensor2 , ripeness );        

      dataset.addValue( 4.0 , sensor3 , battery );               
      dataset.addValue( 3.0 , sensor3 , ripeness );                   

      return dataset; 
   }
   public static void main( String[ ] args )
   {
      SensorLevels chart = new SensorLevels("Sensor Data","Battery and Ripeness Levels");
      chart.pack( );        
      RefineryUtilities.centerFrameOnScreen( chart );        
      chart.setVisible( true ); 
   }
}