int potPin = 2;
int potValue;
char inputBuffer[10];
int delayTime;
int value;

void setup() {
  Serial.begin(9600);
  value = 0;
}

void loop() {
  if (Serial.available() > 0)
  {
    char ret = Serial.read();
    //Serial.println(ret);

    //We read the buffer byte per byte until the end of the delay
    if (ret != '\n') {
     value = 10 * value + (ret - 48);

    //The delay time has been fully received
    } else {
      //Serial.println(value);
      delayTime = value;
      //Serial.println(delayTime);

      //We transmit the potentiometer value then delay the card for the received time
      potValue = analogRead(potPin);
      Serial.println(potValue);
      delay(delayTime);
     
      value = 0;
    }
  }
}
