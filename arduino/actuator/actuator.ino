int value;
int heaterLED = 13;
int coolingLED = 12;

void setup() {
  Serial.begin(9600);
  pinMode(heaterLED, OUTPUT);
  pinMode(coolingLED, OUTPUT);
  digitalWrite(heaterLED, LOW);
  digitalWrite(coolingLED, LOW);
  value = 0;
}

void loop() {
  if (Serial.available() > 0)
  {
    char ret = Serial.read();
    //Serial.println(ret);
    if (ret != '\n') {
    //Getting the value from the byte.
     value = 10 * value + (ret - 48);
    } else {
      //Serial.println(value);

      //Turning the light on or off according to the received value
      if (value > 511) {
        digitalWrite(heaterLED, LOW);
        digitalWrite(coolingLED, HIGH);
      } else  {
        digitalWrite(heaterLED, HIGH);
        digitalWrite(coolingLED, LOW);
      }
      value = 0;
    }
  }
}
