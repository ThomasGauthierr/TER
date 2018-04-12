const String ID = "sensorLDR1";

bool started;
bool DEBUG = false;

short defaultDelayTime = 1000;
unsigned long currentDelayTime;

int LDRPin = 3;
int LDRValue;

void setup() {  
  Serial.begin(9600);
  started = false;
  currentDelayTime = defaultDelayTime;  
}

void loop() {
  
  if (Serial.available() > 0){
    
    char read = Serial.read();
    
    if (read == 'i') {
      Serial.println(ID.c_str());
      started = true;
      delay(defaultDelayTime);
    } else if (read =='c') {
      setup();
    } else if (read == 'd') {
      receiveDelay();
    } else {
      //Do nothing
    }
  }

  if(started){
    LDRValue = analogRead(LDRPin);
    Serial.println(ID + " " + String(LDRValue) + " " + millis());
    delay(currentDelayTime); 
  }
}

void receiveDelay() {
  currentDelayTime = 0;

  char read = Serial.read();
  
  while (read != '\n') {
    if (DEBUG) {
      Serial.println("read : " + String(read - 48));
      delay(1000);
    }
    currentDelayTime = currentDelayTime * 10 + (read - 48);
    read = Serial.read();
  }

  if (currentDelayTime <= 0) {
    currentDelayTime = defaultDelayTime;
  }

  if(DEBUG) {
    Serial.println("delay : " + String(currentDelayTime));
  }
}

