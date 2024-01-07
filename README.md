# Cash_Nest
The 5th Future Finance A.I. Challenge _ KB 국민은행

![image](https://github.com/AI-Toy-Project/Cash_Nest/assets/92291198/bf5b1feb-8f2b-4b7a-981d-2ed1bb3e1645)
<br>

## 👩🏻‍💻 팀구성
|김영진|김수현|방기연|
|:---:|:---:|:---:|
|<img src="https://github.com/AI-Toy-Project/Cash_Nest/assets/141807721/090d51e9-e2f8-4bb4-aa5d-4c5aed7bc821" width="150">|<img src="https://github.com/AI-Toy-Project/Cash_Nest/assets/92291198/d96bc627-8dca-4437-897a-029de690d048" width="150">|<img src="https://github.com/AI-Toy-Project/Cash_Nest/assets/132427506/5019224d-23b9-45e0-946a-5e41420262c4" width="150">|
|[@yxxngjxn](https://github.com/yxxngjxn)|[@susooo](https://github.com/susooo)|[@PangKiYeon](https://github.com/PangKiYeon)|
<br>

## 💻 프로젝트 소개
* 금융 지식이 부족해 자산 관리가 어려운 사람들
* 소비 습관 개선이 필요한 사람들
* 소액으로도 저축을 하고 싶은 사람들

이러한 분들에게 저축의 재미를 알리기 위한 <Cash Nest> 애플리케이션을 구현하였습니다!
<br>

### 💰CASH NEST란?💰

* 최근 소비 내역을 바탕으로 AI가 다음 달 소비 금액을 예측
* 예측 소비 금액과 실제 소비 금액을 비교하여 더 적게 소비했을 경우 그 차액만큼 자동 적금
* 이달의 적금에 성공하면 성취를 느낄 수 있는 스탬프 지급
* 쉽고 간편한 애플리케이션 형식 및 편리한 UI 디자인
<br>

## 📌 프로젝트 기능
+ 소비 패턴 확인하기
  카테고리 별로 저번달에 AI가 예측한 이번달 소비량(회색) 과 현재 자신의 소비량(노랑색) 을 나타낸다.
  예측액보다 현재 소비액이 적은 만큼 예상 적금금액이 불어난다.
<p align="center"><img src="https://github.com/AI-Toy-Project/Cash_Nest/assets/92291198/df3b8639-5db2-4206-98b5-ccef9226eb16" style="width:300px">
</p>
<br>

+ 적금 통장 확인하기
  우리의 서비스를 통해 저축한 적금 통장을 보여준다.
  최종적으로 한달 소비액이 예측액보다 적으면 성취도장을 찍어준다. 도장판은 1년마다 바뀐다.
<p align="center"><img src="https://github.com/AI-Toy-Project/Cash_Nest/assets/92291198/bac13cfb-7202-466a-9e06-0eb9bc6fcade" style="width:300px">
</p>
<br>

## ⚙️ 기술 설명

![004](https://github.com/AI-Toy-Project/Cash_Nest/assets/132427506/9c96cb58-6454-4fb9-a69f-7f965d546de2)

![005](https://github.com/AI-Toy-Project/Cash_Nest/assets/132427506/54d32d1f-59f6-4e3a-910f-4af6b69a7676)

### 📍KoBERT Model
* 한국어 텍스트 데이터의 시퀀스 패턴을 학습한 후, 결재 가맹점명을 기반으로 각 내역의 카테고리 분류를 수행하기 위해 사용

### 📍LSTM Model
* 과거의 시퀀스 패턴을 학습한 후, 최근 3개월 데이터를 기반으로 다음달 소비 금액을 카테고리별로 예측

