int LDRPin = 3;
int LDRValue;
int delayTime;

unsigned long lastTime;
unsigned long currentTime;
unsigned long timestamp;

// CAUTION : The IDs have to be different from
//           one device to another.
//           A sensor has to contains "sensor" somewhere in its name
//           but not "actuator".

const String ID = "sensorLDR1";

void setup() {
  Serial.begin(9600);
  LDRValue = 0;
  lastTime = 0;
}

void loop() {
  
  if (Serial.available() > 0)
  {
    char read = Serial.read();
    
    //Sending the value followed by the timestamp if a 'v' is read
    if (read == 'v') {

      if (lastTime == 0) {
        lastTime = millis();        
      }
      
      LDRValue = analogRead(LDRPin);

      currentTime = millis();
      timestamp = currentTime - lastTime;
      lastTime = currentTime;
      
      Serial.println(String(LDRValue) + " " + String(timestamp));
      
      //Send the ID if a 'i' is received
    } else if (read == 'i') {
      Serial.println(ID.c_str());
      
    } else {
      //Do nothing if anything else is received
    }
  }
}
