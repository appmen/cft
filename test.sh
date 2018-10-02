#!/bin/bash

OS=`uname`

if [[ "$OS" == 'Linux' ]]; then
   chmod +x driver/geckodriver_linux
   mvn -Dwebdriver.gecko.driver=driver/geckodriver_linux clean test
elif [[ "$OS" == 'Darwin' ]]; then
   chmod +x driver/geckodriver_osx
   mvn -Dwebdriver.gecko.driver=driver/geckodriver_osx clean test
fi
