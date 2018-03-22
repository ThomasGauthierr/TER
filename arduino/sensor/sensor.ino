int potPin = 2;
int potValue;
int delayTime;
bool valueSent;

void setup() {
  Serial.begin(9600);
  valueSent = false;
  potValue = 0;
}

void loop() {
  if (!valueSent) {
    potValue = analogRead(potPin);
    Serial.println(potValue);
    valueSent = true;
  }

  if (Serial.available() > 0 && valueSent)
  {
    char ret = Serial.read();

    //We read the buffer byte per byte until the end of the delay
    if (ret != '\n') {
     value = 10 * value + (ret - 48);

    //The delay time has been fully received
    } else {
      delayTime = value;

      //We transmit the potentiometer value then delay the card for the received time
      potValue = analogRead(potPin);
      Serial.println(potValue);
      delay(delayTime);

      value = 0;
      valueSent = true;
    }
  }
}
