# nifi-env.sh
## NiFi home directory 정의 
* 별도 설정이 없을 경우 binary가 압축해제된 디렉토리가 NIFI_HOME으로 지정된다.
  * 수정을 원할 경우
    * '압축이 해제된 디렉토리'/bin/nifi-env.sh 파일의 42번째 줄을 수정한다. 
  * 기본값
    * NIFI_HOME="$(setOrDefault "$NIFI_HOME" "$(cd "$SCRIPT_DIR" && cd .. && pwd)")"
    * 예시)  NIFI_HOME=/home/bigdata/nifi
  
## PID 파일 저장 경로 정의
* '압축이 해제된 디렉토리'/bin/nifi-env.sh 파일의 46번째 줄을 수정한다. 


## log 파일 저장 경로 정의
* '압축이 해제된 디렉토리'/bin/nifi-env.sh 파일의 50번째 줄을 수정한다. 

## JAVA HOME 설정
* JDK 혹은 JRE 설정을 참조하여 설정한다.
  * '압축이 해제된 디렉토리'/bin/nifi-env.sh 파일의 26번째 줄을 수정한다.
  * 예시) export JAVA_HOME=/home/java/jdk1.8.0
