char inputBuffer[10];
int value;
int i;
int j;

void setup() {
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);
  i = 0;
  j = 0;
}

void loop() {
  i++;
  if (Serial.available() > 0)
  {
    value = 0;
    Serial.readBytes(inputBuffer, 10);
    value = atoi(inputBuffer);
    if (value > 750) {
      digitalWrite(LED_BUILTIN, HIGH);
      delay(1000);
    } else {
      digitalWrite(LED_BUILTIN, LOW);      
    }
  }
  if (i == 10000) {
    i = 0;
    j++;
    if (j = 10000) {
      j = 0;
      
      Serial.println(value);
      Serial.flush();
    }
  }
}
