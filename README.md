# Flow : Block-File-Extensions

### 과제 기간

2024/08/13 - 2024/08/17 23시까지

## 과제 고려사항

### 기술

- JAVA 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Thymeleaf
- AWS EC2
- Github action
- Docker

### 깃 전략

main → develop → feature

main : 상용 서버에 적용될 브랜치

develop : 개발 브랜치

feature : 각 기능을 구현할 브랜치
- feat/{name}-#{issue_number}

### 깃 컨벤션

- feat : 기능 구현
- refactor : 수정 사항
- test : 테스트 작성
- docs : 리드미 작성
- fix : 버그 수정
- chore : 세팅 업무

### 개발 고려사항

ERD
![erd.png](erd.png)