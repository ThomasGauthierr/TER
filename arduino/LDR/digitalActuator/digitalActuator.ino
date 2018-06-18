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
const String ID = "1100LigAc";

String intensity;
int ledPin = 9;
bool receivingValue;

float currentIntensity = 0;
byte luminosite;

void setup() {
  Serial.begin(9600);
  pinMode(ledPin, OUTPUT);
  receivingValue = false;
  currentIntensity = 0;
  analogWrite(ledPin, 255 * currentIntensity);
  intensity = "OFF";
}

void loop() {
  if (Serial.available() > 0)
  {
    char read = Serial.read();
    if (receivingValue) {
      //Receiving the intensity char per char
      //The intensity has to be HIGH, MEDHIGH, MEDLOW, or LOW
      if (read != '\n') {
        intensity += read;

      //When we reach the end of the word,
      //we light the LED according to the received intensity.
      } else {
        if (intensity == "OFF") {
            currentIntensity = 0;
        } else {
            currentIntensity = 1;
        }

        analogWrite(ledPin, 255 * currentIntensity);
        receivingValue = false;
      }
    }
    
    //Receiving the value if a 'v' is read
    if (read == 'v') {      
      receivingValue = true;
      intensity = "";
            
    //Send the ID if a 'i' is received
    } else if (read == 'i') {
      Serial.println(ID.c_str());

    } else if (read == 'c') {
      setup();

    } else if (read == 't') {
      Serial.println(intensity.c_str());

    } else {
      //Do nothing if anything else is received
    }
  }
}
