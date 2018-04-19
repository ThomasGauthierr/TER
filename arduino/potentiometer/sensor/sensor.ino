/** CAUTION : The IDs have to be different from
 *           one device to another and be 8 chars long.
 *
 *  The ID is composed as XYZId where :
 *  - X allows the program to know what kind of device it is :
 *     --> 0 : SENSOR
 *     --> 1 : ACTUATOR
 *  - Y corresponds to the data type influenced by the device :
 *     --> 0 : TEMPERATURE
 *     --> 1 : LIGHT
 *  - Id : you can put whatever you want here.
**/
const String ID = "sensorPot1";

bool started;
bool DEBUG = false;

short defaultDelayTime = 1000;
unsigned long currentDelayTime;

int potPin = 2;
int potValue;

void setup() {
  Serial.begin(9600);
  started = false;
  currentDelayTime = defaultDelayTime;
}

void loop() {

  if (Serial.available() > 0){

    char read = Serial.read();

    if (read == 'i') {
      Serial.println(ID.c_str());
      started = true;
      delay(defaultDelayTime);
    } else if (read =='c') {
      setup();
    } else if (read == 'd') {
      receiveDelay();
    } else {
      //Do nothing
    }
  }

  if(started){
    potValue = analogRead(potPin);
    Serial.println(ID + " " + String(potValue) + " " + millis());
    delay(currentDelayTime);
  }
}

void receiveDelay() {
  currentDelayTime = 0;

  char read = Serial.read();

  while (read != '\n') {
    if (DEBUG) {
      Serial.println("read : " + String(read - 48));
      delay(1000);
    }
    currentDelayTime = currentDelayTime * 10 + (read - 48);
    read = Serial.read();
  }

  if (currentDelayTime <= 0) {
    currentDelayTime = defaultDelayTime;
  }

  if(DEBUG) {
    Serial.println("delay : " + String(currentDelayTime));
  }
}
