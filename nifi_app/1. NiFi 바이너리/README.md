# NiFi 1.14.0 바이너리
## nifi-1.14.0-bin.tar.gz
* NiFi 구동을 위해 java 1.8이상이 필요하다.
* 서비스를 위해 8080(변경 가능) 포트 개방이 필요하다.

### 설치 
* CentOS 7.5 기준
* bigdata:bigdata 계정:그룹으로 구동하고 bigdata 계정의 home 디렉토리에 설치 하는 것으로 가정한다.

1. 바이너리 첨부된 파일을 사용하거나 공식 홈페이지에서 다운로드 한다.
   * https://nifi.apache.org/ - [Download] - Release 메뉴에서 다운로드 할 수 있다.
   * 예시) wget https://archive.apache.org/dist/nifi/1.14.0/nifi-1.14.0-bin.tar.gz 
2. 압축 해제
   <pre><code>
   # 바이너리 파일이 다운로드 된 곳에서
   tar -xvzf nifi-1.14.0-bin.tar.gz 
   </code></pre>

### 구동
1. 압축이 해제된 디렉토리로 이동한다.
   <pre><code>
   cd ~/nifi-1.14.0
   </code></pre>
2. 서비스를 시작한다.
   <pre><code>
   bin/nifi.sh start
   </code></pre>

### 종료 및 재시작
1. 종료
   <pre><code>
    bin/nifi.sh stop
   </code></pre>
2. 재시작
   <pre><code>
   bin/nifid.sh restart 
   </code></pre>
 
