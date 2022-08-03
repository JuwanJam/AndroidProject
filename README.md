# AndroidProject

<p align="center">
 <img src="https://user-images.githubusercontent.com/90138160/165911868-ce7b9b0e-08c7-4563-b6ee-6a387c11626e.png"> 
</p>


## [SketchDay 접속 링크](http://58.236.95.130:9999/)를 통해서 저희의 서비스 이용이 가능합니다!!


# :blue_book:SketchDay : 내 일기의 감정을 통하여 노래를 추천 받고 그림일기로 볼 수 있는 서비스
> 2022.04.11 ~ 2022.05.10 AI 빅프로젝트
* 일기를 쓰면서 자신의 고민과 일상을 털어놓고 감정분석을 통해 내 감정 변화를 보여주고 내 일기와 비슷한 다른 사용자의 일기를 추천 받고 노래를 추천받아 듣고 AI를 통해 사용자가 쓴 일기를 그림으로 표현을 해 주면서 다른 사람들과 그림 일기를 공유하며 사용자의 추억을 저장하는 서비스 입니다.
# 목차
[1. 조원 소개](#1-조원-소개)

[2. 배경 및 목적](#2-배경-및-목적)

[3. 주요 서비스 화면](#3-주요-서비스-화면)

[4. Service Flow](#4-service-flow)

[5. Architecture](#5-architecture)

[6. ERD](#6-erd)

[7. 실행 방법](#7-실행-방법)


# 1. 조원 소개
#### [5조]수도권 2반 2조
>  김동현(팀장), 강민서, 김세진, 전성호, 황인원

# 2. 배경 및 목적
* 시대가 급격하게 변해가면서 바쁘고 지친 하루를 보내고 있는 사람들이 늘어나고 있습니다. 또한 매년 우울증과 스트레스가 증가하고 있습니다. 그런 사용자들의 마음의 짐을 덜고 바빴던 일상이 추억이 될 수 있도록 서비스를 제공하고 싶었습니다. 추억을 남기면서 우울증과 스트레스를 해소할 수 있는 좋은 방법으로 일기가 떠올랐고 이미 서비스화 되어있는 단순한 일기보다는 특별한 요소가 있어 일기를 쓰는 재미와 우울증, 스트레스 해소에 더 도움을 줄 수 있는 컨텐츠를 생각했고, 작성 한 일기의 감정에 따라 음악을 듣고 그것을 그림일기 형태로 저장할 수 있다면 더 오래 추억에 남고 재미를 줄 수 있을 것 이라고 생각했습니다.
#### :bulb: 즉, 일기 분석을 통한 사용자의 감정변화를 보여주고 내 일기와 비슷한 다른 사용자의 일기, 노래 추천 및 그림일기 생성 서비스를 통해 사용자들의 추억을 저장하고 특별한 소통 공간을 만들고자 했습니다.
&nbsp;
* **타켓층**
  * 지친 일상생활에서 위로 받고 싶은 사람 
  * 하루를 기록하기를 원하는 사람
  * 자신의 하루를 공유하기 원하는 사람
  * 다른 사람의 일기를 공유 받고 싶은 사람
  * 자신의 일기를 그림일기로 남기고 싶은 사람

&nbsp;

* **주요 기능**
  * 작성된 일기를 분석하여 감정 및 주제를 추출하고 표현된 감정 및 주제에 알맞은 노래 추천  (노래 평가 가능)
  * 내가 작성한 일기와 비슷한 다른 사용자 일기 추천
  * 사용자가 작성 한 일기를 AI를 통해 요약해서 그림일기로 표현
  * 작성된 일기 데이터를 통해 캘린더에 감정 이모티콘 표시 및 워드 클라우드 생성
  
&nbsp;

![AI 5조 포스터](https://user-images.githubusercontent.com/90138160/167758253-69d4e3f3-6b39-4930-8aea-15feef5c145e.jpg)

# 3. 주요 서비스 화면
> 토글 클릭하시면 이미지 확인이 가능합니다.
<details>
    <summary>메인 달력화면</summary>
 
![image](https://user-images.githubusercontent.com/90138160/167751290-e1fe1f35-2cc1-47b4-99b4-f7062553dde0.png)
 
 <summary>이번 달 워드클라우드 및 감정선</summary>
 
 ![image](https://user-images.githubusercontent.com/90138160/167753532-4a80d045-79a1-40b5-83d5-eff6424eb57a.png)
 
</details>
<details>
    <summary>결과창</summary>
 
![InkedKakaoTalk_20220510_131857649_LI](https://user-images.githubusercontent.com/90138160/167750556-8a1a5de2-7f67-4162-a6dc-3ac767bdf6ff.jpg)
 
</details>

<details>
    <summary>공유 일기 리스트</summary>
 
![image](https://user-images.githubusercontent.com/90138160/167752496-7146bf10-3e1a-42ee-ab63-b34f6107ed1b.png)
 
</details>

<details>
    <summary>내 프로필</summary>
 
![image](https://user-images.githubusercontent.com/90138160/167753541-03f101ad-23b5-48b2-bdf1-c20b8f86eb6c.png)
 
</details>


# 4. Service Flow
<p align="center">
 <img src="https://user-images.githubusercontent.com/90138160/165701902-97f4d696-584c-4155-8116-7c38d8e43640.png"> 
</p>


# 5. Architecture
![아키텍처 정의서](https://user-images.githubusercontent.com/45118610/167749427-fdfed6e1-6316-4c36-94a0-27e96ad70f84.PNG)
![architecture](https://user-images.githubusercontent.com/29485153/167747788-55849e07-8379-4d9a-9a93-e36383704e56.png)

# 6. ERD
![에이블스쿨 AI 빅프로젝트_ERD_05조 (1) (1)](https://user-images.githubusercontent.com/66732995/167747338-8f355dcc-b2aa-48c0-a31e-b95080965fb0.png)

# 7. 실행 방법
### 1. 파이썬 설치(v3.x)

### 2. 파이썬 가상환경 생성 및 실행

### 3. 원하는 위치에서 SketchDay 프로젝트 clone 진행 및 프로젝트 폴더 최상단으로 위치 이동

### 4. pip install -r requirements.txt 실행

### 5. [구글 드라이브](https://drive.google.com/file/d/153Cqkgfj_U7C0oEaAAjgtezDVEl3YmqZ/view?usp=sharing) 클릭해서 압축파일(용량이 큰 모델 및 디폴트 이미지 파일) 다운로드 후 압축 해제

### 6. 압축 해제후 
#### - 다운로드 파일의 media 파일은 프로젝트 최상단에 복사
#### - 다운로드 파일의 diary/ml_models 내의 모델 파일도 프로젝트파일 내의 같은 위치에 복사

#### - 다운로드 파일의 task/ml 내의 models 폴더를 프로젝트파일 내의 같은 위치에 복사

### 7. python manage.py makemigrations 실행

### 8. python manage.py migrate 실행

### 9. 각 환경에 맞는 [redis-server](https://redis.io/docs/getting-started/) 공식문서 링크에서 설치 후 실행

### 10. python manage.py runworker background_tasks 실행

### 11. python manage.py runserver
