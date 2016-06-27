#!/bin/bash

SCRIPT_DIR=`pwd`

echo -e "waiting for device....."

adb wait-for-device
 
echo -e "devices connected....."
adb devices
 
adb shell mkdir -p /sdcard/benchmarks/dacapo
adb push $SCRIPT_DIR/data /sdcard/benchmarks/dacapo/
adb install $SCRIPT_DIR/etalon-dacapo.apk