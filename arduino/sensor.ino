int potPin = 2;
int potValue;
char inputBuffer[10];
int delayTime;

void setup() {
  Serial.begin(9600);
}

void loop() {
  if (Serial.available() > 0) {
    Serial.readBytes(inputBuffer,10);
    delayTime = atoi(inputBuffer);
    
    potValue = analogRead(potPin);
    Serial.println(potValue);
    delay(delayTime);
  }
}
