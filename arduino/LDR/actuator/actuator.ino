int LED = 9;
bool receivingValue;

/** CAUTION : The IDs have to be different from
 *           one device to another.
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
 *  - Id : you can put whatever you want here.
**/

const String ID = "110LigAc";
String intensity;

float currentIntensity = 0;
byte luminosite;

void setup() {
  Serial.begin(9600);
  pinMode(LED, OUTPUT);
  receivingValue = false;
  intensity = "";
  currentIntensity = 0;
  analogWrite(LED, 255 * currentIntensity);
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
        if (intensity == "HIGH") {
            currentIntensity = 1;
        } else if (intensity == "MEDHIGH") {
            currentIntensity = 0.7;
        } else if (intensity == "MEDLOW") {
            currentIntensity = 0.4;
        } else if (intensity == "LOW") {
            currentIntensity = 0.1;
        } else {
            currentIntensity = 0;
        }

        analogWrite(LED, 255 * currentIntensity);
        intensity = "";
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
}
