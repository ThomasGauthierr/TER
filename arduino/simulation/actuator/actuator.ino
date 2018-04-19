int value;
int LED = 12;
bool receivingValue;

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
 *  - Z corresponds to the action the actuator is supposed to generate
 *     --> 0 : INCREASE
 *     --> 1 : DECREASE
 *     --> 2 : NONE
 *     --> 3 : OK
**/

const String ID = "100SimAc";

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
