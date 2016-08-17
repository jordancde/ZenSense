/*
 * Prototype Sensor Relay (BLE)
 * 
 * August 2016
 * Isidor Ehrlich
 * ZENsense Project
 * Develloped using libraries and code samples from Arduino LLC and Intel Corporation
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
BLEService sensorDataService("19B10001-E8F2-537E-4F6C-D104768A1214");//This uses a random UUID
BLEUnsignedIntCharacteristic etheneTrans("19B10001-E8F2-537E-4F6C-D104768A1214", BLERead | BLENotify);//This code is a modified version of an online sample

void setup() {
  //Constructor
  pinMode(indicatorPin, OUTPUT);
  sensorRelay.setLocalName("Sensor Relay");
  sensorRelay.setAdvertisedServiceUuid(sensorDataService.uuid());
  sensorRelay.addAttribute(sensorDataService);
  sensorRelay.addAttribute(etheneTrans);
  etheneTrans.setValue(2000);//This sets the default transmited data to 2000 (actual values can be from 0 to 1023
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

