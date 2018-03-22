int potPin = 2;
int potValue;
char inputBuffer[10];
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
  
  if (Serial.available() > 0 && valueSent) {
    Serial.readBytes(inputBuffer,10);
    delayTime = atoi(inputBuffer);    
    delay(delayTime);
    valueSent = false;
  }
}
