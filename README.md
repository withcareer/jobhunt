# 채용 공고 쉽고 한눈에 보기위해 만든 웹 어플리케이션 - JobHunt



https://user-images.githubusercontent.com/79193811/210316218-fb668f0a-9519-4b1e-899c-5ac4cf8feab3.mp4


## 프로젝트 기간

* 2022-12 ~ 2021-1.

### 팀원

* 김민우, 이창훈




### 상세내용

* <span style='background-color: #f6f8fa'>Spring framework</span>와  <span style='background-color: #f6f8fa'>React</span>와 관련한 기술 숙련도를 높이고자 2명에서 진행하였 습니다. 클라이언트는 잡코리아에서 동적크롤링으로 가져온 채용정보를 통해서 한눈에 손쉽게 채용공고를 확인할 수 있습니다.


### 사용한 기술 및 라이브러리

* Spring framework

* React

* Docker

* AWS EC2

* Google Driver

* Git Action

* Selenium

* JWT 

+ Redis

* Swagger API

### Swagger API 문서로 만든 Restful api 문서

![image](https://user-images.githubusercontent.com/79193811/210316563-c8a8c2e1-d6b4-447d-8419-ee5777cc6ac3.png)


### 깨달은점

* 백엔드만 해온 제가 react를 접해보면서 어떤식으로 렌더링이 되는지 useEffect useState를 어느때 사용하는 지 알 수있었습니다.

* Axios 비동기 통신을 이용하여 GET,POST API를 호출하고 보내는 방식을 알게 되었습니다.

* Restful Api 에서는 Session을 잘 사용하지않고 JWT를 이용하여 로그인을 한다는것을 알게 되었습니다.

* JWT 로그인은 Header,payload,Signature 로 구성이 되고 hedaer에 담아서 사용한다는것을 알게되었습니다.

* 동적크롤링을 통해 셀레니움 구글드라이브를 사용해보았으며, div.class명을 통해 크롤링을 하는방법을 깨달았습니다.

* git action을 통해 pipleline을 구축하고 cron을 통해 주기적으로 json파일이 만들어지게 함으로써 주기적인 업데이트를 가능하게 할 수있다는것을 깨달았습니다.

* rest api 와 restful api의 차이점을 알게 되었습니다.

* Swagger API를 사용함으로써 API문서작성 방법을 터득하였습니다.

* JWT에서 보안을 위해 Refresh토큰값의 만료시간은 길고, access Token값의 만료시간은 짧게 설정하여 주기적으로 access token값을 변경해야 한다는 것을 깨달았습니다.

* redis를 사용하여 주기적으로 token값을 바꿔주는 Refresh토큰값을 캐시메모리에 저장시켜 트래픽을 최소화 시켰습니다.

* 백엔드에서 React.js 라우터에 있는 주소로 주소를 임의 변경하면, 매핑되는 주소가 없으므로 404 오류가 생긴다. 이를 방지하기 위해 에러가 발생하면 프론트엔드에서 작성한 frontend/src/index.html을 전송한다는 것을 알게되었습니다.

###### 참고자료
* https://dev-coco.tistory.com/97
* https://minwoo-it-factory.tistory.com/category/Redis


