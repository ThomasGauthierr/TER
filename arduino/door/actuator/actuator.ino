/** CAUTION : The IDs have to be different from
 *           one device to another.
 *
 *  The ID is composed as WXYZId where :
 *  - W allows the program to know what kind of device it is :
 *     --> 0 : SENSOR
 *     --> 1 : ACTUATOR
 *  - X corresponds to the data type influenced by the device :
 *     --> 0 : TEMPERATURE
 *     --> 1 : LIGHT
 *  - Y corresponds to the action the actuator is supposed to generate
 *     --> 0 : INCREASE
 *     --> 1 : DECREASE
 *     --> 2 : NONE
 *     --> 3 : OK
 *  - Z corresponds to the type of respond the actuator produce
 *     --> 0 : Numeric
 *     --> 1 : Analogic
 *  - Id : you can put whatever you want here.
**/
const String ID = "1011Door";

bool receivingValue;
String intensity;

int switchPin = 3;
int switchState;

int defaultDelayTime = 1000;

void setup() {
  Serial.begin(9600);
  receivingValue = false;
  pinMode(switchPin, INPUT);
}

void loop() {
  switchState = digitalRead(switchPin);

  if (switchState == 0) {
    intensity = "OFF";
  } else {
    intensity = "HIGH";
  }

  if (Serial.available() > 0)
  {
    char read = Serial.read();
    if (receivingValue) {
      if (read != '\n') {
        //Do nothing
      } else {
        receivingValue = false;
      }
    }

    //Receiving the value if a 'v' is read
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

  delay(defaultDelayTime);
}
