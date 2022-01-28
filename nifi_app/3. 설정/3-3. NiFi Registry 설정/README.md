  # NiFi registry 1.14.0 바이너리
## nifi-1.14.0-bin.tar.gz

* NiFi 구동을 위해 java 1.8이상이 필요하다.
* 서비스를 위해 8080(변경 가능) 포트 개방이 필요하다.

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
  




# nifi-registry.properties

## 서비스(UI 접속)를 위한 설정
  '압축이 해제된 디렉토리/'conf/nifi.properties 파일의 18,19번째 줄을 수정한다.
  nifi.registry.web.http.host='nifi-registry가 구동되는 서버의 호스트명 또는 IP'
  nifi.registry.web.http.port='사용하고자 하는 포트'
  
## Repository DBMS 설정
  '압축이 해제된 디렉토리/'conf/nifi.properties 파일의 58~62번째 줄을 수정한다.
  nifi.registry.db.url=jdbc:mysql://{FQDN}:{Port}
  nifi.registry.db.driver.class=org.mariadb.jdbc.Driver
  nifi.registry.db.driver.directory=/home/manager/apache/registry/lib
  nifi.registry.db.username={DB id}
  nifi.registry.db.password={DB pw}
  
  
  
# NiFi - NiFi-registry 연동
1. Bucket 생성
 1.1 NiFi-Registry 웹 접속 후 우측 상단의 스패너 아이콘 선택
 ![Kafka Consume compressed message (SmartCity_NiFi_Template-02)](./2-3.png)
 1.2. [New Bucket] 선택
  그림 2 
 1.3. Bucket Name 입력
  그림 3
  
2. NiFi 설정
 2.1 NiFi 웹 접속 후 우측 상단의 메뉴 아이콘 선택 후 [Controller Settings] 선택
 그림 11
 2.2 [Registry Clients] 선택
 그림 12
 2.3 NiFi-Registry 서버의 이름, URL, 설명 입력
 그림 13
 
3. Flow version control 설정
 3.1 버전 관리 대상 프로세스 또는 프로세스 그룹 선택 후 마우스 오른쪽 버튼 클릭
     [Verwion]-[Start version control] 선택
	 그림 21
 3.2 버전 관리 대상 NiFi-Registry와 Bucket 선택 후 Flow Name 입력
     그림 22
	 
