#!/bin/bash

dt=$(date '+%Y%m%d')
dd=$(date '+%d')
yymm=$(date '+%Y%m')

REPOSITORY=/home/cbnu/deploy/monitoring/SMARTCITY_ETM_MANAGER
PROJECT_NAME=nifi-spring-boot-api

cd $REPOSITORY/$PROJECT_NAME/

echo "> Start Buiding the Project"

mvn clean install

echo "> crud 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

#cp $REPOSITORY/$PROJECT_NAME/target/*.jar $REPOSITORY/$PROJECT_NAME/
#cp $REPOSITORY/$PROJECT_NAME/configprops.json $REPOSITORY/$PROJECT_NAME/
cp $REPOSITORY/$PROJECT_NAME/target/*.jar $REPOSITORY/
cp $REPOSITORY/$PROJECT_NAME/configprops.json $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}*.jar)

echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/ | grep *.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

#echo "> Absolute Path: $REPOSITORY/$PROJECT_NAME/$JAR_NAME"
echo "> Absolute Path: $REPOSITORY/$PROJECT_NAME"

#nohup java -jar -Dserver.port=9990 -Dapp.config_file_path=$REPOSITORY $REPOSITORY/$JAR_NAME 2>&1 &

nohup java -jar -Dserver.port=13984 -Dapp.config_file_path=$REPOSITORY $REPOSITORY/$JAR_NAME > $REPOSITORY/$PROJECT_NAME/logs/${dt}_${PROJECT_NAME}.log &

#  $REPOSITORY/$PROJECT_NAME/$JAR_NAME 2>&1 &
