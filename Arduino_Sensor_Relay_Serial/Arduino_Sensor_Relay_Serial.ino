/*
 * Prototype Sensor Relay (Serial)
 * 
 * August 2016
 * Isidor Ehrlich
 * ZENsense Project
 * Develloped using libraries from Arduino LLC
 */


//Variables
const int indicatorPin = 13;
const int sensorPin = A0;
int sensorValue = 2000; //actual range is 0-1023

void setup() {
  //Constructor
  pinMode(indicatorPin, OUTPUT);
  pinMode(sensorPin, INPUT);
  digitalWrite(sensorPin, LOW);
  Serial.begin(9600);
  Serial.println("Startup Complete");
}

void loop() {
 //Inifinite While Loop
 digitalWrite(indicatorPin, HIGH);
 sensorValue = analogRead(sensorPin);
 Serial.println(sensorValue);
 digitalWrite(indicatorPin, LOW);
 delay(1000);
}
