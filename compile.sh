#!/usr/bin/env bash
set -e
mkdir -p bin
javac -d bin *.java
echo "Compilation successful! Run ./run.sh to start."
