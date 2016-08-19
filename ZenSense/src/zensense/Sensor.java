/*
 * Prototype Sensor Reciever (Read From File)
 * 
 * August 2016
 * Isidor Ehrlich
 * ZENsense Project
 * Develloped using libraries from Oracle Corporation
 */

package zensense;
import java.io.*;

public class Sensor{
    
    //Variables and Objects
    String path;
    FileReader fileReader;
    BufferedReader reader;
    String nextLine;
    int sensorValue;
    
    //Constructor
    public Sensor(String filePath){
        //Initializing Variables
        nextLine = "";
        path = filePath;
        sensorValue = 2000;
        
        //Initializing File Reader
        try{
             fileReader = new FileReader(path);
             reader = new BufferedReader(fileReader);
        }
        catch(FileNotFoundException ex) {
            System.err.println("Unable to open file : " + path);                
        }
    }
    
    //Reads the next number in the file and returns it
    public int readSensor(){
        try{
            nextLine = reader.readLine();
            if(!nextLine.equals("Startup Complete")){
                sensorValue = Integer.parseInt(nextLine);
            }
            else{
                nextLine = reader.readLine(); //Skips to the next line
                sensorValue = Integer.parseInt(nextLine);
            }
        }
        catch(IOException ex){
            System.err.println("Unable to read file : " + path);
        }
        return sensorValue;
    }
}
