#include <Servo.h>
#include <SoftwareSerial.h>
#include <NewPing.h>
#include <Adafruit_PWMServoDriver.h>

// wi-sun용 시리얼 통신 포트
#define RX 10 // blue
#define TX 11 // green

#define MOTOR_DRIVER_VCC    8

// 초음파 센서
#define TRIGGER_PIN  6 // gray
#define ECHO_PIN     7 // yellow
#define MAX_DISTANCE 500

// 서보모터
Servo servo;
// 모터 드라이버-서보모터
Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver();

// 기본 시리얼 통신 포트 데이터
char serial_data = '0';

// wi-sun용 시리얼 통신 포트 지정
SoftwareSerial mySerial(RX, TX);
// wi-sun용 시리얼 통신 포트 데이터
char myserial_rx = '0'; 

// 초음파 센서
NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);
uint8_t myserial_tx = 0;





void setup() {
  Serial.begin(9600);
  mySerial.begin(9600);

  servo.attach(9);

  pinMode(MOTOR_DRIVER_VCC, OUTPUT);

  pwm.begin();
  pwm.setPWMFreq(51);
}


void loop() {
  digitalWrite(MOTOR_DRIVER_VCC, HIGH);


  // 키보드 입력 (서보 작동 테스트용)
  if (Serial.available()) {
    // serial_data = Serial.read();
    // Serial.println("From keyboard: "); Serial.print(serial_data);

    // if (serial_data == '0') { // 올림
    //   Serial.println("motor up");
    //   servo.write(90);
    //   delay(100);
    // } else if (serial_data == '1') { // 내림
    //   Serial.println("motor down");
    //   servo.write(0);
    //   delay(100);
    // }

    // 시리얼 모니터로 값 입력
    int a = Serial.parseInt();
    // 받은 값의 범위 0~180을 펄스길이 150~600으로 매핑
    // ra의 최대 최소를 150, 600을 넘지 않게
    int ra = constrain(map(a, 0, 180, 150, 600), 150, 600);

    // pca9685 모듈의 0번 포트에 연결된 서보를 ra만큼 회전
    pwm.setPWM(0, 0, ra);

    // 시리얼 모니터에 출력
    Serial.print('('); Serial.print(a); Serial.print(','); Serial.print(ra); Serial.println(')');
    delay(10);
  }

  // Wi-SUN -> 아두이노 (서보 제어)
  if (mySerial.available()) {
    myserial_rx = mySerial.read(); // UART1으로 서보 제어 값 읽기
    Serial.println("Servo control get: "); Serial.println(myserial_rx);

    // 모터 제어
    if (myserial_rx == '0' || myserial_rx == 0) { // 주차 안됨: 차단바 올림
      // servo.write(90); // TODO: pwm으로 변경
      pwm.setPWM(0, 0, constrain(map(45, 0, 180, 150, 600), 150, 600));
      Serial.print("myserial: "); Serial.println("0");
      delay(1000);
    } else if (myserial_rx == '1' || myserial_rx == 1) { // 주차 됨: 차단바 내림
      // servo.write(0); // TODO: pwm으로 변경
      pwm.setPWM(0, 0, constrain(map(135, 0, 180, 150, 600), 150, 600));
      Serial.print("myserial: "); Serial.println("1");
      delay(1000);
    }
  }

  // 아두이노 -> Wi-SUN (초음파 값 전송)
  myserial_tx = (uint8_t)sonar.ping_cm(); // 초음파 센서 값 읽기
  if (myserial_tx > 200) {
    myserial_tx = 200;
  }
  mySerial.write(myserial_tx); // UART1으로 초음파 센서 값 쓰기
  Serial.print("Sonar value send: "); Serial.println(myserial_tx); // 시리얼 모니터에 출력
  delay(1000);
}
