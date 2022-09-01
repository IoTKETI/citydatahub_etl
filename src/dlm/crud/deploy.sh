#!/bin/bash

dt=$(date '+%Y%m%d')
dd=$(date '+%d')
yymm=$(date '+%Y%m')

REPOSITORY=/home/cbnu/deploy/dlm/crud
PROJECT_NAME=smartcity-dlm-crud
SERVICE_NAME=datalifecycle-crud

cd $REPOSITORY/$PROJECT_NAME/

echo "> 프로젝트 Build 시작"

mvn clean package

echo "> crud 디렉토리로 이동"

cd $REPOSITORY

echo "> Build 파일 복사"

cp $REPOSITORY/$PROJECT_NAME/target/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${SERVICE_NAME}*.jar)

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

nohup java -jar \
	-Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,$REPOSITORY/application-real-db.properties $REPOSITORY/$JAR_NAME > $REPOSITORY/logs/${dt}_${SERVICE_NAME}.log 2>&1 &
