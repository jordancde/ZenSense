
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
    
    public static void main(String[] args) throws IOException {
        if(!(new File(FILEPATH).canRead())){
            (new File(FILEPATH)).createNewFile();
        }
        d = new Date();
        double[] fakedata = new double[] {5.2,4.3,50.4};
        writeFile(fakedata);
        ArrayList<String[]> data = readFile();
        System.out.println(data.get(0)[1]);
    }
    
    public static double[] pullData(int numSensors){
        double[] ethaneData = new double[numSensors];
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
}
