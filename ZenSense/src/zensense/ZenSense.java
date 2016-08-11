
package zensense;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;


public class ZenSense {

    public static Date d = new Date();
    public static String FILEPATH;
    public static final int NUMSENSORS = 6;
    public static final int NUMHORIZONTAL = 2;
    public static final int NUMVERTICAL = 3;
    public static final int RIPEVOLTAGE = 5;
    public static final int GRIDSIZE = 500;
    public static final int RIPEDAYS = 21;
    public static Date dataDate;
    

    
    public static void main(String[] args) throws IOException, Exception {
        URL location = ZenSense.class.getProtectionDomain().getCodeSource().getLocation();
        FILEPATH = location.getFile()+"data.csv";
        
        if(!(new File(FILEPATH).canRead())){
            (new File(FILEPATH)).createNewFile();
        }
        
        
        d = new Date();
        //Time, Voltage, Battery, Xpos%, Ypos%, SensorID
        double[] fakedata;
        double[][] positions = new double[NUMSENSORS][2];
        for(int i = 0;i<NUMSENSORS;i++){
            for(int j = 0;j<2;j++){
                positions[i][0] = i%NUMHORIZONTAL*(100/NUMHORIZONTAL)+25;
                positions[i][1] = i%NUMVERTICAL*(100/NUMVERTICAL)+18;
            }
        }
        
        for(int i = 0;i<NUMSENSORS;i++){
            fakedata = new double[] {d.getTime(),Math.random()*5,Math.random()*100,positions[i][0],positions[i][1],i};
            
            writeFile(fakedata,d.getTime());
        }
        
        SensorLevels.main(args);
        HeatMapFrame.main(args);        
        
    }
    
    public static double[] pullData(){
        double[] ethaneData = new double[NUMSENSORS];
        
        return ethaneData;
    }
    
    public static void writeFile(double[] data, long time) throws IOException{
        
        
        try(
            FileWriter fw = new FileWriter(FILEPATH, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {       
            out.print(time+",");
            out.print(data[1]+",");
            out.print(data[2]+",");
            out.print(data[3]+",");
            out.print(data[4]+",");
            out.print(data[5]+",");
            out.println("");
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("Something Fucked up");
        }
    
    }
    
    public static ArrayList<String[]> readFile() throws IOException{
        
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
    //historicIndex is how far back requested, 0 is most current
    public static double[][] generateHeatMapData(int historicIndex) throws IOException{
        double[][] data = new double[GRIDSIZE][GRIDSIZE];
        for(int i = 0;i<data.length;i++){
            for(int j = 0;j<data[i].length;j++){
                data[i][j] = 1;
            }
        }
        ArrayList<String[]> previousCheck = new ArrayList<String[]>();
        ArrayList<String[]> rawData = readFile();
        dataDate = new Date(Long.parseLong(rawData.get(rawData.size()-1-NUMSENSORS*historicIndex)[0]));
        
        for(int i = 0;i<NUMSENSORS;i++){
            previousCheck.add(rawData.get(rawData.size()-1-NUMSENSORS*historicIndex-i));
        }
        
        
        
        double distance = 0;
        for(int i = 0;i<data.length;i++){
            for(int j = 0;j<data.length;j++){
                distance = 0;
                for(String[] sensor:previousCheck){
                    
                    double x = Double.parseDouble(sensor[3]);
                    double y = Double.parseDouble(sensor[4]);
                    distance +=  Math.sqrt(Math.pow((int)(x/100*GRIDSIZE)-i,2)+Math.pow((int)(y/100*GRIDSIZE)-j,2));

                }
                
                double averageVoltage = 0;
                for(String[] sensor:previousCheck){
                    double x = Double.parseDouble(sensor[3]);
                    double y = Double.parseDouble(sensor[4]);
                    double voltage = Double.parseDouble(sensor[1]);
                    double distancePercent = (Math.sqrt(Math.pow((int)(x/100*GRIDSIZE)-i,2)+Math.pow((int)(y/100*GRIDSIZE)-j,2)))/distance;
                    averageVoltage += voltage*distancePercent;
                }
                data[i][j] = averageVoltage/RIPEVOLTAGE;
                
            }
                
        }
        
        
        
        return data;
    }
    
    
}
