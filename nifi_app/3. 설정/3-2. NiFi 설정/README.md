  # bootstrap.conf
  ## NiFi 어플리케이션 구동 설정
  ### 처리하는 데이터가 많을 경우 
  * nifi가 구동되는 서버의 사양을 감안하여 수정한다.
     * '압축이 해제된 디렉토리/'conf/bootstrap.conf 파일의 38,39번째 줄을 수정한다.
     * java heap 과관련된 설정으로써 최소 값과 최대 값을 적당히 가감한다.
  
  ## 그외 구동 옵션과 관련한 java 옵션 수정
  * '압축이 해제된 디렉토리/'conf/bootstrap.conf 파일의 끝에 추가 한다.
    * java.arg.18='추가하고자 하는 옵션'
    * 옵션을 더 추가하는 경우 변수 번호를 1씩 증가하면서 추가한다.



# nifi.properties
## NiFi 설정 및 튜닝 관련 설정 정의
### 서비스(UI 접속)를 위한 설정
* '압축이 해제된 디렉토리/'conf/nifi.properties 파일의 153,154번째 줄을 수정한다.
  * nifi.web.http.host='nifi가 구동되는 서버의 호스트명 또는 IP'
  * nifi.web.http.port='사용하고자 하는 포트'
***
## 그 외 설정은 nifi.properties.ref 파일의 주석을 참고하여 수정한다.
