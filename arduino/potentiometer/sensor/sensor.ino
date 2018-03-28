int potPin = 2;
int potValue;
int delayTime;

// CAUTION : The IDs have to be different from
//           one device to another.
//           A sensor has to contains "sensor" somewhere in its name
//           but not "actuator".

const String ID = "sensorPot1";

void setup() {
  Serial.begin(9600);
  potValue = 0;
}

void loop() {

  if (Serial.available() > 0)
  {
    char read = Serial.read();

    //Sending the value if a 'v' is read
    if (read == 'v') {
      potValue = analogRead(potPin);
      Serial.println(potValue);

      //Send the ID if a 'i' is received
    } else if (read == 'i') {
      Serial.println(ID.c_str());

    } else {
      //Do nothing if anything else is received
    }
  }
}