/* 
  This sketch reads the temperature and analog weigh input and
  writes them to scratch characteristics 1 and 2 respectively.
  
  The arduino is only woken fom sleep when a conection to the
  LightBlue Bean is established. After reading the weight and
  temperature the arduino microcontroller is put to sleep to
  conserve battey power.
  
  Writen By: Kyle Shaw
  
  Code is based off of example code from the LightBlue Bean
  website  
 
*/

int controlPin = 3;//Pin for weight measurement

void setup() {
  Bean.enableWakeOnConnect( true );//wake the arduino up when a BT connection is made
 //use serial and windows loder application to debug
 // Serial.begin(); 
  pinMode(controlPin, OUTPUT); 
}

void loop() {
  bool connected = Bean.getConnectionState();
  uint8_t buffer[1];
  uint16_t weight; 
  ScratchData thisScratch; 

  if(connected){
   //Measure weight (AI has 10 bits resolution)
   digitalWrite(controlPin,HIGH);
   //delay(3); 
   //Each weigt is 10lbs. Each weight is given range of 51/1024 bits
   weight = (20-(analogRead(A0)/51))*10;
   digitalWrite(controlPin,LOW);
  
  //adjust calculation for open circuit measurement 
  if(weight < 15)
    weight = 10;
   
  // Serial.println(weight);
   
   buffer[0]= Bean.getTemperature(); // Returns the temperature in the format "24" in degrees celcius)
   Bean.setScratchData(1, buffer, 1);//write the temperature to scratch 1 
    
   buffer[0]= weight; 
   Bean.setScratchData(2, buffer, 1);//write the weight scratch 2
   
   Bean.sleep(100);//turn the arduino off for 100 ms
  }
  else{
    Bean.sleep(0xFFFFFFFF);//sleep unless woken
  }
}
