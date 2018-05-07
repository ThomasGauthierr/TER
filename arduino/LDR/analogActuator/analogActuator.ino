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
const String ID = "1001LigAc";

String intensity;
bool receivingValue;
int ledPin1 = 9;
int ledPin2 = 10;

float led1Intensity = 0;
float led2Intensity = 0;
byte luminosite;

void setup() {
  Serial.begin(9600);
  pinMode(ledPin1, OUTPUT);
  pinMode(ledPin2, OUTPUT);
  receivingValue = false;
  intensity = "";
  led1Intensity = 0;
  led2Intensity = 0;
  analogWrite(ledPin1, 255 * led1Intensity);
  analogWrite(ledPin2, 255 * led2Intensity);
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
            led1Intensity = 1;
            led2Intensity = 1;
        } else if (intensity == "MEDHIGH") {
            led1Intensity = 1;
            led2Intensity = 0.3;
        } else if (intensity == "MEDLOW") {
            led1Intensity = 1;
            led2Intensity = 0;
        } else if (intensity == "LOW") {
            led1Intensity = 0.5;
            led2Intensity = 0;
        } else {
            led1Intensity = 0;
            led2Intensity = 2;
        }

        analogWrite(ledPin1, 255 * led1Intensity);
        analogWrite(ledPin2, 255 * led2Intensity);
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
