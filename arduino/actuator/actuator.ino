char * inputBuffer;
int value;

void setup() {
  Serial.begin(9600);
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, LOW);
  value = 0;
  inputBuffer = malloc(sizeof(char) * 10);
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
      if (value > 750) digitalWrite(LED_BUILTIN, HIGH);
      else digitalWrite(LED_BUILTIN, LOW);
      value = 0;
    }
  }
}
