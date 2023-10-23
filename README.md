[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/fnZ3vxy8)

# **WI-SUN FAN 네트워크를 활용한 스마트 공유 주차장**

# 1. 프로젝트 소개
'주차난' 이라고 표현할 만큼 차량의 주차와 관련된 문제가 많습니다. 기존에 이런 문제를 해결하기 위한 여러 공유 주차장 서비스들이 제공되어 왔지만 '부정 주차'로 인한 한계가 있었습니다. 

![모듈 모델링](./docs/files/module%20structure.png)
저희는 위의 그림처럼 '부정 주차'를 방지할 수 있도록 차단기가 설치된 모듈을 제작하였습니다. 그리고 이를 활용해 기존의 문제를 해결한 공유 주차장 서비스를 제작했습니다. 

# 2. 팀 소개
- 정우영
- 이창주
- 홍유준

# 3. 구성도
- 아래의 그림은 전체적인 서비스의 구조도를 나타내는 그림입니다. 
![서비스 전체 구조도](./docs/files/structure.jpg)
- 어플리케이션 - 서버: Firebase의 FireStore를 어플리케이션 서버로 활용
- AP(AccessPoint): AP에서는 python을 활용하여 firestore document의 eventListener를 등록해, 어플리케이션과 통신
- AP - WI-SUN 루트: AP에 연결된 


# 4. 소개 및 시연 영상
[시연 영상](https://youtube.com/)

# 5. 사용법
> 이 프로젝트에 사용된 wi-sun 모듈의 경우 기술적 권리로 인해 배포 및 공개가 금지되어 있습니다. 그래서 해당 모듈에 관련된 소스코드 및 사용법은 이 repository 및 README에 포함되어 있지 않습니다.
## 5.1 Arduino
## 5.2 Observer
## 5.3 Android
## 5.4 Casing(H/W)
- 