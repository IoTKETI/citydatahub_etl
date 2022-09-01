#!/bin/bash

# shellcheck disable=SC2034
ROOT_DIR=/usr/share/nginx/html
APP_DIR=nifi-monitoring-app-master
NGINX_FILE=nginx-nifi-monitor-app-config.conf

echo "> Starting..."

chmod +x ./env.sh && ./env.sh && cp env-config.js ./public/

echo "> Installing the dependencies"

npm install
sleep 2

echo "> Building the project"
yarn build
sleep 2

if [ -d $ROOT_DIR/$APP_DIR ]
then
    echo "Directory $ROOT_DIR/$APP_DIR"
    sudo rm -rf $ROOT_DIR/$APP_DIR/*
else
    echo "Creating Directory $APP_DIR"
    sudo mkdir $ROOT_DIR/$APP_DIR
fi


sudo cp -r build/* $ROOT_DIR/$APP_DIR/

#NGINX Version 1.17.7

sudo cp $NGINX_FILE /etc/nginx/conf.d/

echo "> Restarting NGINX Server"
sudo systemctl restart nginx

sleep 1
echo ">>> DONE!"




