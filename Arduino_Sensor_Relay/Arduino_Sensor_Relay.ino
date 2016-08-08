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
BLEService sensorDataService("f348dd41-c9e9-46fa-8542-621dd90ffb1c");//This uses a random UUID
BLECharacteristic etheneTrans("f348dd41-c9e9-46fa-8542-621dd90ffb1c", BLERead | BLENotify, 10);//I don't know what is going on here, this code is a modified version of an example

void setup() {
  //Constructor
  pinMode(indicatorPin, OUTPUT);
  sensorRelay.setLocalName("Sensor Relay");
  sensorRelay.setAdvertisedServiceUuid(sensorDataService.uuid());
  sensorRelay.addAttribute(sensorDataService);
  sensorRelay.addAttribute(etheneTrans);
  etheneTrans.setValue(999);//This should set the etheneTrans characteristic to the value that will be transmitted, but this is not working
  Serial.print("Current UUID : ");
  Serial.println(sensorDataService.uuid());
  sensorRelay.begin();//Start BLE
}

void loop() {
  //Constantly Repeating Loop
  BLECentral hub = sensorRelay.central();
  if(hub.connected()){//Connection Squence
    digitalWrite(indicatorPin, HIGH);
    Serial.print("Connected to : ");
    Serial.println(hub.address());
    while(hub.connected()){
      //Scan sensor and send back to hub
      int etheneData;
      etheneData = analogRead(sensorPin);//Read Ethene Level
      updateHub(etheneData);
    }
  }
  else{
    digitalWrite(indicatorPin, LOW);
  }
}

void updateHub(int EDV){
  //Send data back to the hub via BLE
  int etheneAttributeData;
  etheneAttributeData = EDV;
  etheneTrans.setValue(etheneAttributeData);//Should update the etheneTrans characteristic with a new int value
  Serial.print("Sent Ethene Data To Hub : ");
  Serial.println(etheneAttributeData);
}

