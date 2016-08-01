
package zensense;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;


public class ZenSense {

    public static Date d = new Date();
    public static final String FILEPATH = "/Users/jordandearsley/poo.csv";
    public static final int NUMSENSORS = 5;
    public static final int RIPEVOLTAGE = 5;
    public static final int GRIDSIZE = 500;
    
    public static void main(String[] args) throws IOException, Exception {
        if(!(new File(FILEPATH).canRead())){
            (new File(FILEPATH)).createNewFile();
        }
        d = new Date();
        //Time, Voltage, Battery, Xpos%, Ypos%
        //double[] fakedata = new double[] {5.2,4.3,50.4,50,60};
        //writeFile(fakedata);
        HeatMapFrame.main(args);
    }
    
    public static double[] pullData(){
        double[] ethaneData = new double[NUMSENSORS];
        //BLAHBLAHBLAH;
        //PULLING DATA FROM TRANSMISSION;
        //ethaneData = NUMBERSANDSHIT;
        return ethaneData;
    }
    
    public static void writeFile(double[] data) throws IOException{
        if(!(new File(FILEPATH).canRead())){
            (new File(FILEPATH)).createNewFile();
        }
        
        try(
            FileWriter fw = new FileWriter(FILEPATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {       
            out.print(data[0]+",");
            out.print(data[1]+",");
            out.print(data[2]+",");
            out.print(data[3]+",");
            out.print(data[4]+",");
            out.println("");
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Something Fucked up");
        }
    
    }
    
    public static ArrayList<String[]> readFile() throws IOException{
        if(!(new File(FILEPATH).canRead())){
            (new File(FILEPATH)).createNewFile();
        }
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
    
    public static double[][] generateHeatMapData() throws IOException{
        double[][] data = new double[GRIDSIZE][GRIDSIZE];
        for(int i = 0;i<data.length;i++){
            for(int j = 0;j<data[i].length;j++){
                data[i][j] = 1;
            }
        }
        ArrayList<String[]> previousCheck = new ArrayList<String[]>();
        ArrayList<String[]> rawData = readFile();
        
        
        for(int i = 0;i<NUMSENSORS;i++){
            previousCheck.add(rawData.get(rawData.size()-1-i));
            
        }
        for(String[] sensor:previousCheck){
            
            double voltage = Double.parseDouble(sensor[1]);
            
            System.out.println(voltage/RIPEVOLTAGE);
            double x = Double.parseDouble(sensor[3]);
            double y = Double.parseDouble(sensor[4]);
            double distance = 0;
            for(int i = 0;i<data.length;i++){
                for(int j = 0;j<data.length;j++){
                    distance =  Math.sqrt(Math.pow((int)(x/100*GRIDSIZE)-i,2)+Math.pow((int)(y/100*GRIDSIZE)-j,2));
                    data[i][j] -= (voltage/RIPEVOLTAGE)*(distance/GRIDSIZE)/Math.pow(NUMSENSORS,22);
                }
            }
        }
        
        return data;
    }
    
    
}
