#!/usr/bin/env bash
set -e
mkdir -p bin
javac -d bin *.java
java -cp bin LoginFrame
