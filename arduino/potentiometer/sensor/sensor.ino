int potPin = 2;
int potValue;
int delayTime;
bool isInitialised;
int defaultDelayTime = 2000;
int currentDelayTime;

unsigned long lastTime;
unsigned long currentTime;
unsigned long timestamp;

// CAUTION : The IDs have to be different from
//           one device to another.
//           A sensor has to contains "sensor" somewhere in its name
//           but not "actuator".

const String ID = "sensorPot1";

void setup() {
  Serial.begin(9600);
  potValue = 0;
  lastTime = 0;
  isInitialised = false;
  currentDelayTime = defaultDelayTime;
}

void loop() {

  //If the sensor has been initialised, it will send a message
  //during each iteration through the serial.
  //The message is composed as follows :
  //value *space* timestamp *comma*
  //e.g :750 1000,500 1000,550 1000,
  if (isInitialised) {
      if (lastTime == 0) {
        lastTime = millis();
      }

      potValue = analogRead(potPin);

      currentTime = millis();
      timestamp = currentTime - lastTime;
      lastTime = currentTime;
      Serial.print(String(potValue) + " " + String(timestamp) + ",");
      delay(currentDelayTime);
  }

  if (Serial.available() > 0)
  {
    char read = Serial.read();

    //If the sensor receives a 'i', it will transmit its ID
    //and will start to send datas
    if (read == 'i') {
      Serial.println(ID.c_str());
      isInitialised = true;

    //If the sensor receives a 'd', it will read the
    //the rest of the message to modify its delay time
    } else if (read == 'd') {
      currentDelayTime = 0;
      read = Serial.read();

      while (read != '\n') {
        currentDelayTime = currentDelayTime * 10 + (read - 48);

        read = Serial.read();
      }

    //If a 'c' is read, the sensor go back to the initial state
    } else if (read =='c') {
      setup();

    } else {
      //Do nothing if anything else is received
    }
  }
}
