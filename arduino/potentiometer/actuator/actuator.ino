int value;
int redLED = 13;
int blueLED = 12;
bool receivingValue;

// CAUTION : The IDs have to be different from
//           one device to another.
//           An actuator has to contains "actuator" somewhere in its name
//           but not "sensor".

const String ID = "actuatorPot1";

void setup() {
  Serial.begin(9600);
  pinMode(redLED, OUTPUT);
  pinMode(blueLED, OUTPUT);
  digitalWrite(redLED, LOW);
  digitalWrite(blueLED, LOW);
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
        if (value > 511) {
          digitalWrite(redLED, LOW);
          digitalWrite(blueLED, HIGH);
        } else {
          digitalWrite(redLED, HIGH);
          digitalWrite(blueLED, LOW);
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

    } else {
      //Do nothing if anything else is received
    }
  }
}