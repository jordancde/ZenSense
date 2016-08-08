/*
 * Prototype Sensor Relay (BLE)
 * 
 * August 2016
 * Isidor Ehrlich
 * ZENsense LLC
 * Develloped using libraries from Arduino LLC and Intel Corporation
 */


//Importing ALL BLE Libraries
#include <BLEAttribute.h>
#include <BLECentral.h>
#include <BLECharacteristic.h>
#include <BLECommon.h>
#include <BLEDescriptor.h>
#include <BLEPeripheral.h>
#include <BLEService.h>
#include <BLETypedCharacteristic.h>
#include <BLETypedCharacteristics.h>
#include <BLEUuid.h>
#include <CurieBLE.h>

//Variables
const int indicatorPin = 13;
const int sensorPin = A0;
BLEPeripheral sensorRelay;
BLEService sensorData("TEMP_UUID");//Replace with a real UUID

void setup() {
  //Constructor
  pinMode(indicatorPin, OUTPUT);

  sensorRelay.setLocalName("Sensor Relay");
  sensorRelay.setAdvertisedServiceUuid(sensorData.uuid());
  Serial.print("Current UUID : ");
  Serial.println(sensorData.uuid());
  sensorRelay.begin();
}

void loop() {
  //Constantly Repeating Loop
  BLECentral hub = sensorRelay.central();
  if(hub){//Connection Squence
    digitalWrite(indicatorPin, HIGH);
    Serial.print("Connected to : ");
    Serial.println(hub.address());
    while(hub.connected()){
      //Scan sensor and send back to hub
      int etheneData;
      etheneData = analogRead(sensorPin);
      //etheneData = getEtheneLevel();
      updateHub(etheneData);
    }
  }
}


/* This is not exactly needed
int getEtheneLevel(){
  //Connects to the analog ethene sensor
  int etheneLevel = 9999;
  etheneLevel = analogRead(sensorPin); //checking the analog reading
  return etheneLevel;
}
*/

void updateHub(int EDV){
  //Send data back to the hub via BLE
  int etheneAttributeData;
  etheneAttributeData = EDV;
  //actually update the hub
  Serial.print("Sent Ethene Data To Hub : ");
  Serial.println(etheneAttributeData);
}

