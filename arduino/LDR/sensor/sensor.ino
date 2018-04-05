const String ID = "sensorLDR1";

bool strated;

void setup() {
  Serial.begin(9600);
  strated = false;
}

void loop() {
  
  if (Serial.available() > 0){
    char read = Serial.read();
    if (read == 'i') {
      Serial.println(ID.c_str());
      strated = true;
    } else if (read =='c') {
      setup();
    }
  }

  if(strated){
    Serial.println("10 " + String(millis()));
    delay(2000); 
  }
}
