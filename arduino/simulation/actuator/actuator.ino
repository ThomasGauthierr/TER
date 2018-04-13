int value;
int LED = 12;
bool receivingValue;

// CAUTION : The IDs have to be different from
//           one device to another.
//           An actuator has to contains "actuator" somewhere in its name
//           but not "sensor".

const String ID = "actuatorLDR1";

void setup() {
  Serial.begin(9600);
  pinMode(LED, OUTPUT);
  digitalWrite(LED, LOW);
  value = 0;
  receivingValue = false;
}

void loop() {
  if (Serial.available() > 0)
  {
    char read = Serial.read();
    if (receivingValue) {
      //Receiving the value byte per byte
      if (read != '\n') {
        value = value * 10 + (read - 48);

      //When we reach the end of the value, 
      //we light the corresponding LED.
      } else {
        if (value == 0) {
          digitalWrite(LED, LOW);
        } else {
          digitalWrite(LED, HIGH);
        }
        value = 0;
        receivingValue = false;
      }
    }
    
    //Sending the value if a 'v' is read
    if (read == 'v') {      
      receivingValue = true;
            
      //Send the ID if a 'i' is received
    } else if (read == 'i') {
      Serial.println(ID.c_str());

    } else if (read == 'c') {
      setup();
      
    } else {
      //Do nothing if anything else is received
    }
  }
}
