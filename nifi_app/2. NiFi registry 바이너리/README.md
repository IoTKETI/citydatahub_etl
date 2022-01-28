# NiFi registry 1.14.0 바이너리
## nifi-1.14.0-bin.tar.gz

* NiFi 구동을 위해 java 1.8이상이 필요하다.
* 서비스를 위해 18080(변경 가능) 포트 개방이 필요하다.

### 설치
* CentOS7.5 기준
* bigdata:bigdata 계정:그룹으로 구동하고 bigdata 계정의 home 디렉토리에 설치 하는 것으로 가정한다.
* Repository로 사용할 DBMS가 필요하다. MySQL,PostgresQL 등을 지원한다.
* 데이터베이스 계정 생성 및 DB 생성, 권한 설정이 완료 돼있어야 한다.
* DBMS 연결을 위한 JDBC 드라이버 파일을 '압축이 해제된 디렉토리'/lib 디렉토리에 복사 해둬야 한다.

1. 바이너리 첨부된 파일을 사용하거나 공식 홈페이지에서 다운로드 한다.
 https://nifi.apache.org/ - [Subproject] - [Registry] - Links 메뉴에서 다운로드 할 수 있다.
  예시) wget https://mirror.navercorp.com/apache/nifi/1.14.0/nifi-registry-1.14.0-bin.tar.gz
2. 압축 해제
 (바이너리 파일이 다운로드 된 곳에서) tar -xvzf nifi-registry-1.14.0-bin.tar.gz

 ### 구동
 1. 압축이 해제된 디렉토리로 이동한다.
  예시) cd ~/nifi-registry-1.14.0
 2. 서비스를 시작한다.
  bin/nifi-registry.sh start


 ## 종료 및 재시작
 1. 종료
  bin/nifi-registry.sh stop
 2. 재시작
  bin/nifi-registry.sh restart
