dbapp
=====
한양대학교 데이터베이스 모델링 및 응용 프로젝트

처음 데이터베이스 자료 올리기
=======================
주어진 데이터를 직접 CSV로 변환하고 HeidiSQL을 이용하여 데이터베이스에 추가하였습니다.
처음 데이터베이스 상태를 덤프, 보관하여 나중의 테스트에 활용할 수 있도록 했습니다.
덤프 파일에서 사용한 데이터베이스 이름은 dbapp입니다.

데이터베이스 연결 설정
=================
src/main/java/DatabaseConfig.java에서 변경하십시오.

실행하는 방법
===========
1. dbapp 데이터베이스를 준비합니다.
1. /database/dbapp.sql을 실행하여 데이터를 넣습니다.
1. 데이터베이스 연결 정보를 DatabaseConfig.java에 넣습니다.
1. mvn package && java -jar target/dbapp-0.0.1-SNAPSHOT.jar
1. http://localhost:8080/