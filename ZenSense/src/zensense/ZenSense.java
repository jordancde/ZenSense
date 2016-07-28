
package zensense;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;

public class ZenSense {

    public static Date d = new Date();
    public static final String FILEPATH = "";
    
    public static void main(String[] args) {
        d = new Date();
        
    }
    
    public static double[] pullData(int numSensors){
        double[] ethaneData = new double[numSensors];
        //BLAHBLAHBLAH;
        //PULLING DATA FROM TRANSMISSION;
        //ethaneData = NUMBERSANDSHIT;
        return ethaneData;
    }
    
    public static void writeFile(double[] data){
        try(FileWriter fw = new FileWriter(FILEPATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {       
            out.print(","+data[0]);
            out.print(","+data[2]);
            out.print(","+data[3]);
        } catch (IOException e) {
            System.out.println("Something Fucked up");
        }
    
    }
    
    public static ArrayList<String[]> readFile(){
        
        String line = "";
        String cvsSplitBy = ",";
        ArrayList historicalData = new ArrayList<String[]>();
            
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH))) {

            while ((line = br.readLine()) != null) {
                
                // use comma as separator
                String[] data = line.split(cvsSplitBy);
                historicalData.add(data);
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return historicalData;
    }
}
